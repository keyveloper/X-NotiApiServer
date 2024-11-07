package com.example.liveApiServer.repository



interface LikeQueryDslRepository {
    fun deleteLogically(boardId: Long)
    // Error: Exception : boardId 찾을 수 없음
}