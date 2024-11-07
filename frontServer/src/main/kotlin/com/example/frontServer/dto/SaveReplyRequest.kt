package com.example.frontServer.dto

import jakarta.validation.constraints.NotEmpty
import org.springframework.web.multipart.MultipartFile

data class SaveReplyRequest (
    @field:NotEmpty
    val textContent: String,

    val imgFile: MultipartFile?,

    @field:NotEmpty
    val parentId: Long,
)