package com.example.notiApiServer.config

import com.example.notiApiServer.service.KafkaConsumerRunner
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConsumerConfigRunner(
    val kafkaConsumerRunner: KafkaConsumerRunner
) {
    @Bean
    fun runner(): ApplicationRunner {
        return ApplicationRunner {
            Thread { kafkaConsumerRunner.poll() }.start()
        }
    }
}