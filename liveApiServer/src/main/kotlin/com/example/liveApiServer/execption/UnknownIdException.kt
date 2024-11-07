package com.example.liveApiServer.execption

import com.example.liveApiServer.enum.ServerErrorCode

class UnknownIdException: LikeApiException(ServerErrorCode.UNKNOWN_ID, "Unknown Id.")