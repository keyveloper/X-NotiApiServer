package com.example.liveApiServer.execption

import com.example.liveApiServer.enum.ServerErrorCode

class SaveFailureException: LikeApiException(ServerErrorCode.SAVE_FAILURE, "like failure") {
}