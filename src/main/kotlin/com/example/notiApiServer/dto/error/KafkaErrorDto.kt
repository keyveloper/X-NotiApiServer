package com.example.notiApiServer.dto.error

data class KafkaErrorDto(
    val topic: String,
    val value: String,
    val offset: Long
)