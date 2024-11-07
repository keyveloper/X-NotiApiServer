package com.example.frontServer.dto

data class ServerErrorDto(
    val type: String,
    val title: String,
    val status: Int,
    val detail: String,
    val instance: String
)