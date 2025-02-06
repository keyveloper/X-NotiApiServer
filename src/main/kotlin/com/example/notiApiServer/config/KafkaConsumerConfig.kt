package com.example.notiApiServer.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class KafkaConsumerConfig {

    @Bean
    fun kafkaConsumer(
        @Value("\${spring.kafka.consumer.bootstrap-servers}") kafkaBootstrapServers: String,
        @Value("\${spring.kafka.consumer.group-id}") groupId: String,
        @Value("\${spring.kafka.consumer.key-deserializer}") keyDeserializer: String,
        @Value("\${spring.kafka.congsumer.value-deserializer}") valueDeserializer: String,
        @Value("\${spring.kafka.consumer.auto-offset-reset}") autoOffsetReset: String,
        @Value("\${spring.kafka.consumer.enable-auto-commit}") enableAutoCommit: Boolean,
    ): KafkaConsumer<String, String> {
        val props = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers)
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer)
            put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
            put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit)
        }
        return KafkaConsumer<String, String>(props)
    }
}