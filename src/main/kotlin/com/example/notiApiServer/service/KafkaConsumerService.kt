package com.example.notiApiServer.service

import com.example.notiApiServer.dto.KafkaTestDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {
    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        topics = ["test"],
        groupId = "test-group"
    )
    fun consumerMessage(message: KafkaTestDto) {
        logger.info {"consume!!\nID: ${message.id} message: ${message.message}"}
    }
}