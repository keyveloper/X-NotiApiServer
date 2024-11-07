package com.example.frontServer.enum

enum class ErrorCode {

    SAVE_FAILURE,

    DELETE_FAILURE,

    NOT_FOUND_404,        // wrong url

    UNKNOWN_ID,

    CONNECTION_ERROR,

    PARAMETER_ERROR,  // parameter 누락

    VALIDATION_ERROR, // sign up or param valid error

    CREDENTIALS_ERROR, // login failed

    ACCESS_DENIED,     // failed access premium service

    BODY_TYPE_ERROR,

    FILE_NOT_EXIST,
}