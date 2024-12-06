package com.example.notiApiServer.dto

data class NotificationGetRequest(
    val startId: Long,
    val endId: Long,
    val receiverId: Long,
)