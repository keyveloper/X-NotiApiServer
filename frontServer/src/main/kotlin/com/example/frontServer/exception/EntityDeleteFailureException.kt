package com.example.frontServer.exception

import com.example.frontServer.enum.FrontServerError

class EntityDeleteFailureException: ApplicationException(FrontServerError.DELETE_FAILURE.ordinal, "Entity Delete Failure"){
}