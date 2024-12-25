package org.example.timelineapiserver.dto.response

import org.example.timelineapiserver.entity.Timeline
import java.time.LocalDateTime

data class TimelineServerGetResult(
    val id: Long,
    val boardId: Long,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(timeline: Timeline): TimelineServerGetResult {
            return TimelineServerGetResult(
                id = timeline.id!!,
                boardId = timeline.boardId,
                createdAt = timeline.createdAt!!
            )
        }
    }
}