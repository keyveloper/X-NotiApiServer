package org.example.dto

data class NotificationSaveRequest(
    val publisherId: Long,

    val receiverId: Long,

    val message: String,
)