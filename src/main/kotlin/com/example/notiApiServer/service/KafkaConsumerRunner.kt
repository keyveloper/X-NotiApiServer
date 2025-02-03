package com.example.notiApiServer.service

import com.example.notiApiServer.dto.NotificationSaveRequest
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

import java.time.Duration

@Service
class KafkaConsumerRunner(
    private val kafkaConsumer: KafkaConsumer<String, String>,
    private val objectMapper: ObjectMapper,
    private val notificationRepository: NotificationRepository
) {
    private val logger = KotlinLogging.logger {}
    private val maxRetriesLogger = LoggerFactory.getLogger("com.example.notiApiServer.MaxRetriesLogger")


    @Volatile
    private var consumerRunning = true

    @Volatile
    private var isPaused = false
    private val pauseDurationMillis = 3000L // 3초 대기

    // failure check
    private val failureCountMap = mutableMapOf<Triple<String, Int, Long>, Int>()
    private val maxRetries = 3

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
                    // commit offset !
                    kafkaConsumer.commitSync()
                    logger.info { "Record with offset ${record.offset()} processed and committed." }
                } catch (e: JsonProcessingException) {
                    logger.info {"parsing error occurred: $failureCountMap"}
                    // not sync
                    break
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
            val currentRetry = failureCountMap.getOrDefault(key, 0) + 1
            logger.info {"current key ${key}, currentRetry: $currentRetry"}
            failureCountMap[key] = currentRetry
            if (currentRetry > maxRetries) {
                logger.info {"current retry is over than $currentRetry > $maxRetries"}
                // save log
                maxRetriesLogger.error(json)
                // offset -> +1 enforce
                try {
                    val topicPartition = TopicPartition(topic, partition)
                    val offsetData = OffsetAndMetadata(offset + 1) // offset commit to next
                    kafkaConsumer.commitSync(mapOf(topicPartition to offsetData))
                    logger.info { "Committed offset $offset for partition $partition of topic $topic" }
                    failureCountMap.remove(key)
                } catch (e: Exception) {
                    logger.error {"commit offset failure: (offset, partition, topic) ${offset}, $partition, $topic"}
                }
            }
            throw e
        }
    }

    private fun saveNoti(noti: Notification, record: ConsumerRecord<String, String>) {
        val topicPartition = TopicPartition(record.topic(), record.partition())
        val offset = record.offset()
        try {
            notificationRepository.save(noti)
        } catch (e: DataAccessException) {
            logger.error {"DB error occurred at ${offset}"}
            pauseConsumption(topicPartition)
            // pauseDurationMillis 만큼 대기한 후 resume 시도
            Thread.sleep(pauseDurationMillis)
            resumeConsumption(topicPartition)
        }
    }

    private fun pauseConsumption(topicPartition: TopicPartition) {
        if (!isPaused) {
            kafkaConsumer.pause(listOf(topicPartition))
            isPaused = true
            logger.info { "Paused consumption for partition ${topicPartition.partition()} " +
                    "of topic ${topicPartition.topic()}" }
        }
    }

    private fun resumeConsumption(topicPartition: TopicPartition) {
        if (isPaused) {
            kafkaConsumer.resume(listOf(topicPartition))
            isPaused = false
            logger.info { "Resumed consumption for partition ${topicPartition.partition()} " +
                    "of topic ${topicPartition.topic()}" }

        }
    }
}