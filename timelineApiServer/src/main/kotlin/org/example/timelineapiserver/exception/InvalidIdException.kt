package org.example.timelineapiserver.exception

import org.example.timelineapiserver.dto.error.MSAServerErrorDetails
import org.example.timelineapiserver.enum.MSAServerErrorCode

class InvalidIdException(
    errorDetails: MSAServerErrorDetails
): BusinessException (
    errorCode = MSAServerErrorCode.INVALID_ID,
    errorDetails = errorDetails
)