package org.example.timelineapiserver.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class TimelineGetRequest(

    @field:NotNull(message = "receiver id is required")
    @field:Positive(message = "receiver id must be positive")
    val receiverId: Long,

    @field:NotNull(message = "startBoardId id is required")
    @field:Positive(message = "startBoardId id must be positive")
    val startBoardId: Long,

    @field:NotNull(message = "endBoardId id is required")
    @field:Positive(message = "endBoardId id must be positive")
    val endBoardId: Long,
)