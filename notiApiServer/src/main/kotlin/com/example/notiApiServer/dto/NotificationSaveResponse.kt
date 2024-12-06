package com.example.notiApiServer.dto

import com.example.notiApiServer.enum.NotificationServerCode

data class NotificationSaveResponse(
    override val errorDetails: NotificationServerErrorDetails?,
    override val responseCode: NotificationServerCode
): NotificationServerErrorResponse(errorDetails, responseCode)
