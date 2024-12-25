package org.example.timelineapiserver.dto.response

import org.example.timelineapiserver.dto.error.MSAServerErrorDetails
import org.example.timelineapiserver.dto.error.MSAServerErrorResponse
import org.example.timelineapiserver.enum.MSAServerErrorCode

class TimelineServerSaveResponse(
    override val errorDetails: MSAServerErrorDetails?,
    override val errorCode: MSAServerErrorCode
): MSAServerErrorResponse(errorDetails, errorCode)