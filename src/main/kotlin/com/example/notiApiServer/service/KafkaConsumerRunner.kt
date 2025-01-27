package com.example.notiApiServer.service

import com.example.notiApiServer.dto.NotificationSaveRequest
import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.repository.NotificationRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

import java.time.Duration

@Service
class KafkaConsumerRunner(
    private val kafkaConsumer: KafkaConsumer<String, String>,
    private val notificationRepository: NotificationRepository
): CommandLineRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        val topicName = "noti"
        kafkaConsumer.subscribe(listOf(topicName))

        logger.info {"consume starts"}

        Thread {
            try {
                while (true) {
                    val records = kafkaConsumer.poll(Duration.ofMillis(1000))
                    if (!records.isEmpty) {
                        records.map { logger.info{"parsing record: ${it.value()}"} }
                        val testDtos = jsonToDto(records)
                        logger.info { "deserialization completed: ${testDtos}"}
                        saveAll(testDtos)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    // json deserialization
    fun jsonToDto(records: ConsumerRecords<String, String>): List<NotificationSaveRequest> {
        // Kafka TestDto
        val objectMapper = jacksonObjectMapper()
        return try {
            records.map {
                objectMapper.readValue<NotificationSaveRequest>(it.value())
            }
        } catch (e: Exception) {
            logger.error { "ConsumerRunner error: jsonToDto failed ${e.message}" }
            listOf()
        }
    }

    // save all ()
    fun saveAll(requests: List<NotificationSaveRequest>) {
        notificationRepository.saveAll(
            requests.map {
                Notification(
                    id = null,
                    publisherId = it.publisherId,
                    receiverId = it.receiverId,
                    notificationType = it.notificationType,
                    targetBoardId = it.targetBoardId,
                    createdAt = null
                )
            }
        )
    }
}