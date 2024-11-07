package com.example.frontServer.dto

import org.springframework.web.multipart.MultipartFile


data class FilesDto(
    val files: List<MultipartFile>
)
