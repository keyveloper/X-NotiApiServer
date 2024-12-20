package com.example.notiApiServer.dto

import com.example.notiApiServer.enum.ServerResponseCode

open class NotificationServerErrorResponse(
    open val errorDetails: ServerErrorDetails?,
    open val responseCode: ServerResponseCode
)