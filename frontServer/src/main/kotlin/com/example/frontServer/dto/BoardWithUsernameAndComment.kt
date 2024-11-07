package com.example.frontServer.dto

data class BoardWithUsernameAndComment(
    val boardWithUsername: BoardInfo,
    val comments: List<BoardInfo>
    )

