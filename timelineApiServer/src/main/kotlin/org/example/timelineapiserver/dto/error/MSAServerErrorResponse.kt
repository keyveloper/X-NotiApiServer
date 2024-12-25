package org.example.timelineapiserver.dto.error

import org.example.timelineapiserver.enum.MSAServerErrorCode

open class MSAServerErrorResponse(
    open val errorDetails: MSAServerErrorDetails?,
    open val errorCode: MSAServerErrorCode
)