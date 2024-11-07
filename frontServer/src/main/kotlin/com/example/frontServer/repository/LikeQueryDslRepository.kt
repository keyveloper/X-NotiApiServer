package com.example.frontServer.repository

import org.springframework.stereotype.Repository

@Repository
interface LikeQueryDslRepository {
    fun findUsersByBoardId(boardId: Long): List<Long>
}