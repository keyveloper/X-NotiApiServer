package com.example.notiApiServer.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class KafkaConsumerConfig {

    @Bean
    fun kafkaConsumerProperties(): Properties {
        return Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.17.79:9092")

            // 문자열 역직렬화
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)

            put(ConsumerConfig.GROUP_ID_CONFIG, "window-noti-test-group")

            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

            put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
        }
    }

    @Bean
    fun kafkaConsumer(kafkaConsumerProperties: Properties): KafkaConsumer<String, String> {
        return KafkaConsumer(kafkaConsumerProperties)
    }
}