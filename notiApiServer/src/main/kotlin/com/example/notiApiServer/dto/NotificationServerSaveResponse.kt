package com.example.notiApiServer.dto

import com.example.notiApiServer.dto.error.MSAServerErrorDetails
import com.example.notiApiServer.dto.error.MSAServerErrorResponse
import com.example.notiApiServer.enum.MSAServerErrorCode

data class NotificationServerSaveResponse(
    val savedRow: Int,
    override val errorDetails: MSAServerErrorDetails?,
    override val errorCode: MSAServerErrorCode
): MSAServerErrorResponse(errorDetails, errorCode) {
    companion object {
        fun of(
            savedRow: Int,
            errorDetails: MSAServerErrorDetails?,
            errorCode: MSAServerErrorCode
        ): NotificationServerSaveResponse {
            return NotificationServerSaveResponse(
                savedRow = savedRow,
                errorDetails = errorDetails,
                errorCode = errorCode)
        }
    }
}
