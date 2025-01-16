package com.example.notiApiServer.service

import com.example.notiApiServer.dto.KafkaTestDto
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
        val topicName = "window-test"
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
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                kafkaConsumer.close()
            }
        }.start()
    }

    // json desrialization
    fun jsonToDto(records: ConsumerRecords<String, String>): List<KafkaTestDto> {
        // KafakTestDto
        val objectMapper = jacksonObjectMapper()
        return try {
            records.map {
                objectMapper.readValue<KafkaTestDto>(it.value())
            }
        } catch (e: Exception) {
            logger.error { "ConsumerRunner error: jsonToDto failed ${e.message}" }
            listOf()
        }
    }

    // save all ()
}