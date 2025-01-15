package com.example.notiApiServer.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class KafkaConsumerRunner(
    private val kafkaConsumer: KafkaConsumer<String, String>
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
                    for (record in records) {
                        logger.info {"Received: ${record.value()} (partition=${record.partition()}, offset=${record.offset()})\""}
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                kafkaConsumer.close()
            }
        }.start()
    }

}