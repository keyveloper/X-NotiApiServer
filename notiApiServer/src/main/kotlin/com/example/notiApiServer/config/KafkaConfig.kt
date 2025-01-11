package com.example.notiApiServer.config

import com.example.notiApiServer.dto.NotificationSaveRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory

@Configuration
@EnableKafka
class KafkaConfig(
    private val consumerFactory: ConsumerFactory<String, NotificationSaveRequest>
) {
    @Bean
    fun batchFactory(): ConcurrentKafkaListenerContainerFactory<String, NotificationSaveRequest> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, NotificationSaveRequest>()
        factory.consumerFactory = consumerFactory
        factory.isBatchListener = true
        factory.containerProperties.pollTimeout = 3000
        return factory
    }
}