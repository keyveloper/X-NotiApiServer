package com.example.frontServer.exception

import com.example.frontServer.enum.FrontServerError

class UnKnownIdException: ApplicationException(FrontServerError.UNKNOWN_ID.ordinal, "Unknown Id."){}