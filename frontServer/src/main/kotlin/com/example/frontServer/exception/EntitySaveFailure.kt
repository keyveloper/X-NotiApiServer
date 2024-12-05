package com.example.frontServer.exception

import com.example.frontServer.enum.FrontServerError

class EntitySaveFailure: ApplicationException(FrontServerError.SAVE_FAILURE.ordinal, "Save Failure"){}