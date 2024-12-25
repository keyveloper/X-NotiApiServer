package org.example.timelineapiserver.exception

import org.example.timelineapiserver.dto.error.MSAServerErrorDetails
import org.example.timelineapiserver.enum.MSAServerErrorCode

class DuplicatedIdException(
    errorDetails: MSAServerErrorDetails
): BusinessException(
    errorCode = MSAServerErrorCode.DUPLICATED_ID,
    errorDetails = errorDetails
) {}