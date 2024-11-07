package com.example.frontServer.exception

import com.example.frontServer.enum.ErrorCode

class UnKnownIdException: ApplicationException(ErrorCode.UNKNOWN_ID.ordinal, "Unknown Id."){}