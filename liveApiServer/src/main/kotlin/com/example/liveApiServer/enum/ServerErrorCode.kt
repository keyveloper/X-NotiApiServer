package com.example.liveApiServer.enum

enum class ServerErrorCode(val code: Int, val title: String,) {
    UNKNOWN_ID(10000000, "Unknown identifier"),

    SAVE_FAILURE(10000001, "Failed to save"),

    LIKE_CHANGE_FAILURE(10000002, "Failed to change"),
}

// 0 - post , 1 - like, 2- follow, 3-noti, 4-file, 5- timeline ...