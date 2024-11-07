package com.example.frontServer.dto

import com.example.frontServer.entity.Board

data class BoardInfo(
    val board: Board,
    val username: String,
    val commentCount: Long
    )