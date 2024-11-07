package com.example.frontServer.dto

import java.time.LocalDateTime

data class BoardResult(
    val id: Long,

    val writerName: String,

    val textContent: String,

    val fileApiUrl: String?,

    val createdAt: LocalDateTime,

    val lastModifiedAt: LocalDateTime,

    val readingCount: Long,

    val commentCount: Long
) {
    companion object {
        fun of(boardWithUsername: BoardInfo) : BoardResult? {
            if (!boardWithUsername.board.invalid) {
                return BoardResult(
                    id = boardWithUsername.board.id!!,
                    writerName = boardWithUsername.username,
                    textContent = boardWithUsername.board.textContent,
                    fileApiUrl = boardWithUsername.board.fileApiUrl,
                    createdAt = boardWithUsername.board.createdAt!!,
                    lastModifiedAt = boardWithUsername.board.lastModifiedAt!!,
                    readingCount = boardWithUsername.board.readingCount,
                )
            }
            return null
        }
    }
}