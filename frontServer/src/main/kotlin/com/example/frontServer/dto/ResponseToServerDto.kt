package com.example.frontServer.dto

data class ResponseToServerDto(
    val error: ServerErrorDto?,
    val data: Any?
)