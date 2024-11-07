package com.example.frontServer.repository

import com.example.frontServer.dto.BoardInfo
import com.example.frontServer.dto.BoardWithUsernameAndComment

interface BoardQueryDslRepository {
    fun findAllBoardInfo(): List<BoardInfo>

    fun findByBoardInfoWithComments(id: Long): BoardWithUsernameAndComment?
}