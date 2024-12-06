package com.example.notiApiServer.dto

import com.example.notiApiServer.enum.NotificationServerCode

data class NotificationGetServerResponse(
    val notificationGetResults: List<NotificationGetServerResult>,
    override val errorDetails: NotificationServerErrorDetails?,
    override val responseCode: NotificationServerCode
): NotificationServerErrorResponse(errorDetails, responseCode) {
    companion object{
        fun of(
            results: List<NotificationGetServerResult>,
            errorDetails: NotificationServerErrorDetails?,
            responseCode: NotificationServerCode
        ): NotificationGetServerResponse {
            return NotificationGetServerResponse(
                notificationGetResults = results,
                errorDetails = errorDetails,
                responseCode = responseCode
            )
        }
    }
}