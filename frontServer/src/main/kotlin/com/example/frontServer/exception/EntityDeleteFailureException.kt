package com.example.frontServer.exception

import com.example.frontServer.enum.ErrorCode

class EntityDeleteFailureException: ApplicationException(ErrorCode.DELETE_FAILURE.ordinal, "Entity Delete Failure"){
}