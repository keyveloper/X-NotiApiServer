package com.example.frontServer.dto

import com.example.frontServer.enum.ServiceServerError

data class LikeServerSaveResponse(
    val error: ServiceServerError?,
    val details: ServerErrorDetails?
)