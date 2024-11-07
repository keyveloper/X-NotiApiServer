package com.example.frontServer.controller

import com.example.frontServer.dto.*
import com.example.frontServer.exception.EntityDeleteFailureException
import com.example.frontServer.exception.EntitySaveFailure
import com.example.frontServer.exception.UnKnownIdException
import com.example.frontServer.security.AuthUserDetails
import com.example.frontServer.service.BoardService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class BoardController(
    private val boardService: BoardService,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/boards")
    fun findBoards(): ResponseEntity<BoardResponse> {
        val results  = boardService.findAll();
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = null,
                data = results
            )
        )
    }

    @GetMapping("/board")
    fun findById(@RequestParam id: Long): ResponseEntity<ResponseToClientDto> {
        return boardService.findById(id)?.let {
            ResponseEntity.ok().body(
                ResponseToClientDto(
                    errorCode = null,
                    data = it
                )
            )
        } ?: run {
            throw UnKnownIdException()
        }
        // Exception : notFound
    }

    @PostMapping("/board")
    fun save(
        @Valid @ModelAttribute saveBoardRequest: SaveBoardRequest,
        @AuthenticationPrincipal user: AuthUserDetails
    ): ResponseEntity<ResponseToClientDto> {
        logger.info { "start save baord!! " }
        if (boardService.save(saveBoardRequest, user.getUserId(), user.username)) {
            return ResponseEntity.ok().body(
                ResponseToClientDto(
                    errorCode = null,
                    data = null
                )
            )
        } else throw EntitySaveFailure()
    }


    @DeleteMapping("/board")
    fun delete(@RequestParam id: Long): ResponseEntity<ResponseToClientDto> {
        if (boardService.deleteById(id)) {
            return ResponseEntity.badRequest().body(
                ResponseToClientDto(
                    errorCode = null,
                    data = null
                )
            )
        } else throw EntityDeleteFailureException()
    }
}