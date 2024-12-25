package com.example.frontServer.controller

import com.example.frontServer.dto.file.FileResponse
import com.example.frontServer.service.FileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ImageController(
    private val fileService: FileService
) {
    @GetMapping("/img")
    fun findByToken(
        @RequestBody token: String
    ): ResponseEntity<FileResponse> {
        return ResponseEntity.ok().body(
            FileResponse(
                fileResult = fileService.findFilesByToken(token),
                errorResponse = null
            )
        )
    }
}