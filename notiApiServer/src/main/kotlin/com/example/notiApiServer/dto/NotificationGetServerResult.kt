package com.example.notiApiServer.dto

import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.enum.NotificationType
import java.time.LocalDateTime

data class NotificationGetServerResult(
    val id: Long,

    val publisherId: Long,

    val receiverId: Long, // optional

    val notificationType: NotificationType,

    val boardId: Long?,

    val createdAt: LocalDateTime
) {
    companion object {
        fun of(notification: Notification): NotificationGetServerResult {
            return NotificationGetServerResult(
                id = notification.id!!,
                publisherId = notification.publisherId,
                receiverId = notification.receiverId,
                boardId = notification.boardId,
                notificationType = notification.notificationType,
                createdAt = notification.createdAt!!
            )
        }
    }
}