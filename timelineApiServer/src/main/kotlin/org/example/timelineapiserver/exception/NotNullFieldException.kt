package org.example.timelineapiserver.exception

import org.example.timelineapiserver.dto.error.MSAServerErrorDetails
import org.example.timelineapiserver.enum.MSAServerErrorCode

class NotNullFieldException(
    errorDetails: MSAServerErrorDetails
): BusinessException(
    errorCode = MSAServerErrorCode.NOT_NULL,
    errorDetails = errorDetails
)