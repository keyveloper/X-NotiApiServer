package com.example.frontServer.dto

import java.time.LocalDateTime

data class NotificationDto(
    val message: String,
    val createAt: LocalDateTime,
    var publisherImg: String? = null,
)
