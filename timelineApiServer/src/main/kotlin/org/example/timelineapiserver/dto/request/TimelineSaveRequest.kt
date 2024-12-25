package org.example.timelineapiserver.dto.request

import jakarta.validation.constraints.NotEmpty

data class TimelineSaveRequest(

    @field:NotEmpty(message = "boardId is required")
    val boardId: Long,

    @field:NotEmpty(message = "receiverId is required")
    val receiverId: Long
)