package org.example.dto

data class NotificationRequest(
    val startId: Long,
    val endId: Long,
    val receiverId: Long,
)