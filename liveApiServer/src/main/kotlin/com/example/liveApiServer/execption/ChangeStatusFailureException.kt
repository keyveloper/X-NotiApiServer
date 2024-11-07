package com.example.liveApiServer.execption

import com.example.liveApiServer.enum.ServerErrorCode

class ChangeStatusFailureException: LikeApiException(ServerErrorCode.LIKE_CHANGE_FAILURE, "Like status not changed")