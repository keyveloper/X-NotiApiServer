package com.example.liveApiServer.controller

import com.example.liveApiServer.dto.LikeSaveDto
import com.example.liveApiServer.dto.ResponseToServerDto
import com.example.liveApiServer.repository.LikeRepository
import com.example.liveApiServer.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LikeController(
    private val likeService: LikeService,
) {

    @PostMapping("/like")
    fun save(
        @RequestBody saveDto: LikeSaveDto
    ): ResponseEntity<ResponseToServerDto> {
        likeService.save(saveDto)
        return ResponseEntity.ok().body(
            ResponseToServerDto(
                error = null,
                userIds = null
            )
        )
    }

    @DeleteMapping("/like")
    fun delete(
        @RequestBody boardId: Long
    ): ResponseEntity<ResponseToServerDto> {
        likeService.delete(boardId)
        return ResponseEntity.ok().body(
            ResponseToServerDto(
                error = null,
                userIds = null
            )
        )
    }

    @GetMapping("/like/users")
    fun findUserIdsByBoardId(
        @RequestParam boardId: Long,
    ): ResponseEntity<ResponseToServerDto> {
        return ResponseEntity.ok().body(
            ResponseToServerDto(
                error = null,
                userIds = likeService.findUserIdsByBoardId(boardId)
            )
        )
    }
}