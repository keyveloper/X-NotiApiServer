package com.example.frontServer.dto

import java.time.LocalDateTime

data class BoardWithCommentResponse(
    val id: Long,

    val writerName: String,

    val textContent: String,

    val fileApiUrl: String,

    val createdAt: LocalDateTime,

    val lastModifiedAt: LocalDateTime,

    val readingCount: Long,

    val parentId: Long?,

    val invalid: Boolean,

    val comments: List<BoardResponse>
)