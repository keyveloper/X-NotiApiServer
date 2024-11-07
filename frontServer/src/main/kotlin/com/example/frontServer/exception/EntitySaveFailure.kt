package com.example.frontServer.exception

import com.example.frontServer.enum.ErrorCode

class EntitySaveFailure: ApplicationException(ErrorCode.SAVE_FAILURE.ordinal, "Save Failure"){}