package com.example.notiApiServer.dto

data class ServerErrorDetails(
    val type: String?,     // error url
    val status: Int?,      // Server Error Code
    val title: String?,    // summary of Error
    val detail: String?,   //  (Optional but need)
    val instance: String? //
)