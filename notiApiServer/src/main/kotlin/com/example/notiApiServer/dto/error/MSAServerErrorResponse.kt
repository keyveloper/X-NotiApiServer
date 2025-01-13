package com.example.notiApiServer.dto.error

import com.example.notiApiServer.enum.MSAServerErrorCode

open class MSAServerErrorResponse(
    open val errorDetails: MSAServerErrorDetails?,
    open val errorCode: MSAServerErrorCode
)