package com.example.frontServer.service

import com.example.frontServer.dto.NotificationDto
import com.example.frontServer.dto.NotificationInfoDto
import com.example.frontServer.entity.Notification
import com.example.frontServer.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {

    fun save(notificationInfo: NotificationInfoDto): Boolean {
        val receivers = notificationInfo.receivers
        val notifications: List<Notification> = receivers.map {makeBoardNotification(
            notificationInfo.contentImg,
            it,
            notificationInfo.message
        )}

        notificationRepository.saveAll(notifications)
        return true
    }

    private fun makeBoardNotification(
        publisherImg: String?,
        receiver: Long,
        message: String
    ): Notification {
        return Notification(
            publisherImg = publisherImg,
            receiverId = receiver,
            message = message,
        )
    }

    fun findAllByReceiver(receiver: Long): List<NotificationDto> {
        val notifications: List<Notification> = notificationRepository.findAllByReceiverId(receiver)

        val notificationDtos = notifications.map {
            NotificationDto(
                message = it.message,
                createAt = it.createdAt!!, // never null - spring injected
                publisherImg = it.publisherImg
            )
        }

        notifications.forEach {
            it.isSent = true
        }

        notificationRepository.saveAll(notifications)
        return notificationDtos
    }
}