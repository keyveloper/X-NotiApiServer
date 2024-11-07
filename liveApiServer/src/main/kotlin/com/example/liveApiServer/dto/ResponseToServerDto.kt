package com.example.liveApiServer.dto

data class ResponseToServerDto(
    val error: ServerErrorWithRfc7807?,
    val userIds: List<Long>?
)