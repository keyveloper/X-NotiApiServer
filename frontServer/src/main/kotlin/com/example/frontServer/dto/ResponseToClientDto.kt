package com.example.frontServer.dto

import com.example.frontServer.enum.ErrorCode

data class ResponseToClientDto(
    val errorCode: ErrorCode?,
    val data: Any?
    )
