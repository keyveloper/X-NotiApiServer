package com.example.notiApiServer.dto

import com.example.notiApiServer.enum.NotificationServerCode

open class NotificationServerErrorResponse(
    open val errorDetails: NotificationServerErrorDetails?,
    open val responseCode: NotificationServerCode
)