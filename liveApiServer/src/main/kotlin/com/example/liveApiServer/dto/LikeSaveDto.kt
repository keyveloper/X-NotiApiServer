package com.example.liveApiServer.dto

data class LikeSaveDto(
    val boardId: Long,
    val userId: Long,
    val likeType: Int,
)