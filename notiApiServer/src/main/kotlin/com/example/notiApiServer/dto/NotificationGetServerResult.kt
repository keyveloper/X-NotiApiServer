package com.example.notiApiServer.dto

import com.example.notiApiServer.entity.Notification
import java.time.LocalDateTime

data class NotificationGetServerResult(
    val publisherId: Long,

    val receiverId: Long, // optional

    val message: String,

    val createdAt: LocalDateTime
) {
    companion object {
        fun of(notification: Notification): NotificationGetServerResult {
            return NotificationGetServerResult(
                publisherId = notification.publisherId,
                receiverId = notification.receiverId,
                message = notification.message,
                createdAt = notification.createdAt!!
            )
        }
    }
}