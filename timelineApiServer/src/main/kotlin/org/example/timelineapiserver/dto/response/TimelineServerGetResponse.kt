package org.example.timelineapiserver.dto.response

import org.example.timelineapiserver.dto.error.MSAServerErrorDetails
import org.example.timelineapiserver.dto.error.MSAServerErrorResponse
import org.example.timelineapiserver.enum.MSAServerErrorCode

data class TimelineServerGetResponse(
    val results: List<TimelineServerGetResult>?,
    override val errorDetails: MSAServerErrorDetails?,
    override val errorCode: MSAServerErrorCode
): MSAServerErrorResponse(errorDetails, errorCode) {
    companion object {
        fun of(
            results: List<TimelineServerGetResult>,
            errorCode: MSAServerErrorCode,
            errorDetails: MSAServerErrorDetails?
        ): TimelineServerGetResponse {
            return TimelineServerGetResponse(
                results = results,
                errorDetails = errorDetails,
                errorCode = errorCode
            )
        }
    }
}