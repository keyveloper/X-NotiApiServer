package com.example.notiApiServer.service

import com.example.notiApiServer.dto.NotificationSaveRequest
import com.example.notiApiServer.dto.error.KafkaErrorDto
import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.repository.NotificationRepository
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import org.slf4j.MDC

import java.time.Duration

@Service
class KafkaConsumerRunner(
    private val kafkaConsumer: KafkaConsumer<String, String>,
    private val objectMapper: ObjectMapper,
    private val notificationRepository: NotificationRepository
) {
    private val logger = KotlinLogging.logger {}
    private val kafkaDlqLogger = LoggerFactory.getLogger("com.example.notiApiServer.DLQ")

    @Volatile
    private var consumerRunning = true

    fun poll(vararg args: String?) {
        logger.info {"start consumer runner poll ()"}
        val topicName = "noti"
        kafkaConsumer.subscribe(listOf(topicName))

        logger.info {"consume starts"}

        // Graceful shutdown을 위한 shutdown hook 등록
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Shutdown initiated.")
            consumerRunning = false
            kafkaConsumer.wakeup() // poll 중인 쓰레드를 깨워서 종료
        })

        while (consumerRunning) {
            val records = kafkaConsumer.poll(Duration.ofSeconds(3000))

            for (record in records) {
                logger.info {"record : ${record.topic()}, ${record.offset()}, ${record.value()}"}
                try {
                    val notiSaveRequest = recordToNotiSaveRequest(record)
                    // save in DB
                    val notification = Notification(
                        id = null,
                        publisherId = notiSaveRequest.publisherId,
                        receiverId = notiSaveRequest.receiverId,
                        notificationType = notiSaveRequest.notificationType,
                        targetBoardId = notiSaveRequest.targetBoardId,
                        createdAt = null
                    )
                    // save db
                    saveNoti(notification, record)

                    val topicPartition = TopicPartition(record.topic(), record.partition())
                    val offsetAndMetadata = OffsetAndMetadata(record.offset() + 1)
                    kafkaConsumer.commitSync(mapOf(topicPartition to offsetAndMetadata))

                    // commit offset !
                    logger.info { "Record with offset ${record.offset()} processed and committed." }
                } catch (e: JsonProcessingException) {
                    // skip this record
                    continue
                }
            }
        }
    }

    private fun recordToNotiSaveRequest(record: ConsumerRecord<String, String>): NotificationSaveRequest {
        val topic = record.topic()

        val partition = record.partition()
        val offset = record.offset()
        val json = record.value()
        val key = Triple(topic, partition, offset)
        try {
            return objectMapper.readValue<NotificationSaveRequest>(json)
        } catch (e: JsonProcessingException) {
                logger.error {"toNotiSaveRequest parsing error"}
                // save log
            val errorDto = KafkaErrorDto(
                topic = topic,
                value = json,
                offset = offset
            )
            logKafkaDlqError(errorDto)
                // offset -> +1 enforce
                val topicPartition = TopicPartition(topic, partition)
                val offsetData = OffsetAndMetadata(offset + 1) // offset commit to next
                kafkaConsumer.commitSync(mapOf(topicPartition to offsetData))
            throw e
        }
    }


    private fun logKafkaDlqError(errorDto: KafkaErrorDto) {
        try {
            MDC.put("topic", errorDto.topic)
            MDC.put("offset", errorDto.offset.toString())
            MDC.put("value", errorDto.value)
            kafkaDlqLogger.error("Kafka DLQ error occurred: {}", errorDto)
        } finally {
            MDC.clear()
        }
    }


    private fun saveNoti(noti: Notification, record: ConsumerRecord<String, String>) {
        val topicPartition = TopicPartition(record.topic(), record.partition())
        val offset = record.offset()
        try {
            notificationRepository.save(noti)
        } catch (e: DataAccessException) {
            logger.error {"DB error occurred at ${offset}"}
        }
    }
}