package com.example.frontServer.service

import com.example.frontServer.dto.BoardInfo
import com.example.frontServer.entity.Timeline
import com.example.frontServer.repository.BoardRepository
import com.example.frontServer.repository.TimelineRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TimelineService(
    private val timelineRepository: TimelineRepository,
    private val boardRepository: BoardRepository
) {
    @Transactional
    fun findByReceiverId(receiverId: Long): List<BoardAdditionalInfo> {
        // find all board id in timeline rep
        val timelineBoardIds: List<Long> =
            timelineRepository.findAllByReceiverIdWithInOneDay(receiverId).map {it.boardId}

        // find all board in board rep
        val boardInfos: List<BoardInfo> = boardRepository.findByIdsWithUsername(
            timelineBoardIds
        )

        return boardInfos.map {
            BoardAdditionalInfo.of(
                boardInfo = it,
                likeCount = 0,
                replyCount = 0
            )
        }
    }

    private fun countRepliesById(id: Long) : Long {
        return boardRepository.countRepliesById(id)
    }

    fun save(boardId: Long, followersId: List<Long>): Boolean {
        followersId.map {
            timelineRepository.save(
                Timeline(
                    boardId = boardId,
                    receiverId = it
                )
            )
        }
        return true
    }
}