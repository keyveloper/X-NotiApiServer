package com.example.notiApiServer.dto

import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.enum.NotificationType
import java.time.LocalDateTime

data class NotificationGetServerResult(
    val id: Long,

    val publisherId: Long,

    val receiverId: Long, // optional

    val notificationType: NotificationType,

    val targetBoardId: Long?,

    val createdAt: LocalDateTime
) {
    companion object {
        fun of(notification: Notification): NotificationGetServerResult {
            return NotificationGetServerResult(
                id = notification.id!!,
                publisherId = notification.publisherId,
                receiverId = notification.receiverId,
                targetBoardId = notification.targetBoardId,
                notificationType = notification.notificationType,
                createdAt = notification.createdAt!!
            )
        }
    }
}