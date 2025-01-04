package com.example.frontServer.controller.api

import com.example.frontServer.dto.like.LikeSaveRequest
import com.example.frontServer.security.AuthUserDetails
import com.example.frontServer.service.like.LikeApiService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LikeController(
    private val likeService: LikeApiService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/like")
    fun save(
        @Valid @RequestBody likeRequest: LikeSaveRequest,
        @AuthenticationPrincipal user: AuthUserDetails
    ): ResponseEntity<Valid> {
        likeService.save(likeRequest, user.getUserId())
        return ResponseEntity.ok().build()
    }
}