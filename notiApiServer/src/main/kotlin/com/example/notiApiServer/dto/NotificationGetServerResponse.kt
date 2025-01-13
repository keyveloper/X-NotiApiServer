package com.example.notiApiServer.dto

import com.example.notiApiServer.dto.error.MSAServerErrorDetails
import com.example.notiApiServer.dto.error.MSAServerErrorResponse
import com.example.notiApiServer.enum.MSAServerErrorCode

data class NotificationGetServerResponse(
    val serverResults: List<NotificationGetServerResult>,
    override val errorDetails: MSAServerErrorDetails?,
    override val errorCode: MSAServerErrorCode
): MSAServerErrorResponse(errorDetails, errorCode) {
    companion object{
        fun of(
            results: List<NotificationGetServerResult>,
            errorCode: MSAServerErrorCode,
            errorDetails: MSAServerErrorDetails?
        ): NotificationGetServerResponse {
            return NotificationGetServerResponse(
                serverResults = results,
                errorDetails = errorDetails,
                errorCode = errorCode
            )
        }
    }
}