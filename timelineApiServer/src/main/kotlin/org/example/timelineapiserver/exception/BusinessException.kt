package org.example.timelineapiserver.exception

import org.example.timelineapiserver.dto.error.MSAServerErrorDetails
import org.example.timelineapiserver.enum.MSAServerErrorCode

open class BusinessException(
    val errorCode: MSAServerErrorCode,
    val errorDetails: MSAServerErrorDetails
): RuntimeException()