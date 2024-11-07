package com.example.liveApiServer.execption

import com.example.liveApiServer.enum.ServerErrorCode

open class LikeApiException(val errorCode: ServerErrorCode, message: String): RuntimeException(message)