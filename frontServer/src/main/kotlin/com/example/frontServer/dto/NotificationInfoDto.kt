package com.example.frontServer.dto

data class NotificationInfoDto(
    val publisherImg: String? = null,
    val message: String,
    val contentImg: String? = null,
    val receivers: List<Long>
)
