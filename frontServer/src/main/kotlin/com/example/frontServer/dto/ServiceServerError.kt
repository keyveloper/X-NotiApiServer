package com.example.frontServer.dto

import com.example.frontServer.enum.ServiceServerErrorCode

data class ServiceServerError(
    val details: ServerErrorDetails,
    val code: ServiceServerErrorCode
)