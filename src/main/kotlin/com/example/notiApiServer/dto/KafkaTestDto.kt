package com.example.notiApiServer.dto

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

data class KafkaTestDto(
    val id: Long,
    val message: String
)

val t: String
