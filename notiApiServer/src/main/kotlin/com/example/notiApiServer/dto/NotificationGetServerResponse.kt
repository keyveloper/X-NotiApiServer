package com.example.notiApiServer.dto

import com.example.notiApiServer.enum.ServerResponseCode

data class NotificationGetServerResponse(
    val notificationGetResults: List<NotificationGetServerResult>,
    override val errorDetails: ServerErrorDetails?,
    override val responseCode: ServerResponseCode
): NotificationServerErrorResponse(errorDetails, responseCode) {
    companion object{
        fun of(
            results: List<NotificationGetServerResult>,
            errorDetails: ServerErrorDetails?,
            responseCode: ServerResponseCode
        ): NotificationGetServerResponse {
            return NotificationGetServerResponse(
                notificationGetResults = results,
                errorDetails = errorDetails,
                responseCode = responseCode
            )
        }
    }
}