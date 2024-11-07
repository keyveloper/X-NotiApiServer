package com.example.liveApiServer.service

import com.example.liveApiServer.dto.LikeSaveDto
import com.example.liveApiServer.entity.Like
import com.example.liveApiServer.repository.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
) {
    fun save(saveDto: LikeSaveDto){
        likeRepository.save(
            Like(
                id = null, // 이렇게 명시하는게 좋아.
                boardId = saveDto.boardId,
                userId = saveDto.userId,
                likeType = saveDto.likeType,
            )
        )
        // boardId userId가 없는 경우 어떻게 처리?
        // likeType이 없는 type인 경우도 있을 수 있다.
        // Exception?
    }

    fun delete(boardId: Long) {
        likeRepository.deleteLogically(boardId)
    }

    fun findUserIdsByBoardId(boardId: Long): List<Long> {
        return likeRepository.findByBoardId(boardId).map {it.userId}
    }
}