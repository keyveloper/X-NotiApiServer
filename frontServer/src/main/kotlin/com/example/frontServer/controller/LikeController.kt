package com.example.frontServer.controller

import com.example.frontServer.dto.ResponseToClientDto
import com.example.frontServer.security.AuthUserDetails
import com.example.frontServer.service.LikeService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LikeController(
    private val likeService: LikeService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/like")
    fun save(
        @RequestBody boardId: Long,
        @AuthenticationPrincipal user: AuthUserDetails
    ): ResponseEntity<ResponseToClientDto> {
        val isSaved: Boolean = likeService.save(boardId, user.getUserId()) // not null

    }

    @GetMapping("/like/users")
    fun findUsersByBoardId(
        @RequestParam boardId: Long
    ): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = null,
                data = likeService.findAllByBoardId(boardId)
            )
        )
    }
}