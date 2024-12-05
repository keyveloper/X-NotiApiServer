package com.example.frontServer.dto

import com.example.frontServer.enum.FrontServerError

data class ResponseToClientDto(
    val errorCode: FrontServerError?,
    val data: Any?
    )
