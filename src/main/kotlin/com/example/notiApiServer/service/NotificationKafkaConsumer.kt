package com.example.notiApiServer.service

import com.example.notiApiServer.dto.NotificationSaveRequest
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class NotificationKafkaConsumer(
    private val notificationService: NotificationService
) {
    @KafkaListener(
        topics = ["noti"],
        groupId = "notiApiServer-group",
        containerFactory = "batchFactory"
    )
    fun consumeNotifications(request: List<NotificationSaveRequest>) {
        notificationService.saveAll(request)
    }
}