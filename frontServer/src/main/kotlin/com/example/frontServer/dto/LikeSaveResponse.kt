package com.example.frontServer.dto

import com.example.frontServer.enum.FrontServerError

// controller to client
data class LikeSaveResponse(
    val error: FrontServerError?
) {
    companion object{
        fun of (result: LikeSaveResult)
    }
}