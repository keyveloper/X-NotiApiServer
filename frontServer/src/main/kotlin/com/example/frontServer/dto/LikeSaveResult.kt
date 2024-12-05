package com.example.frontServer.dto

import com.example.frontServer.enum.FrontServerError

// service to controller
data class LikeSaveResult(
    val error: FrontServerError?
)