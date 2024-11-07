package com.example.frontServer.dto

import java.time.LocalDateTime

data class BoardResponse(
    val id: Long,

    val writerName: String,

    val textContent: String,

    val fileApiUrl: String?,

    val firstWritingTime: LocalDateTime,

    val lastModifiedTime: LocalDateTime,

    val readingCount: Long,

    val commentCount: Long,

    val likeCount: Long
    ) {
    companion object {
        fun of(boardResult: BoardResult, likeCount: Long) : BoardResponse {
            return BoardResponse(
                id = boardResult.id,
                writerName = boardResult.writerName,
                textContent = boardResult.textContent,
                fileApiUrl = boardResult.fileApiUrl,
                firstWritingTime = boardResult.createdAt,
                lastModifiedTime = boardResult.lastModifiedAt,
                readingCount = boardResult.readingCount,
                commentCount = boardResult.commentCount,
                likeCount = likeCount
                )
        }
    }
}