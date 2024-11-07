package com.example.liveApiServer.dto

import com.example.liveApiServer.enum.ServerErrorCode

// use only when error occur
data class ServerErrorWithRfc7807(
    val type: String?,     // error url
    val status: Int,      // Server Error Code
    val title: String,    // summary of Error
    val detail: String?,   //  (Optional but need)
    val instance: String? //
)