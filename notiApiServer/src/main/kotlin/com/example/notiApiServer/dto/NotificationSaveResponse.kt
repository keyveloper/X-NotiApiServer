package com.example.notiApiServer.dto

import com.example.notiApiServer.enum.ServerResponseCode

data class NotificationSaveResponse(
    val savedRow: Int,
    override val errorDetails: ServerErrorDetails?,
    override val responseCode: ServerResponseCode
): NotificationServerErrorResponse(errorDetails, responseCode) {
    companion object {
        fun of(
            savedRow: Int,
            errorDetails: ServerErrorDetails?,
            responseCode: ServerResponseCode
        ): NotificationSaveResponse {
            return NotificationSaveResponse(
                savedRow = savedRow,
                errorDetails = errorDetails,
                responseCode = responseCode
            )
        }
    }
}
