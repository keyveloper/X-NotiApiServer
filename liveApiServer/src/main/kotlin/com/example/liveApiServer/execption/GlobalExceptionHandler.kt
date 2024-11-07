package com.example.liveApiServer.execption

import com.example.liveApiServer.dto.ResponseToServerDto
import com.example.liveApiServer.dto.ServerErrorWithRfc7807
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handleCustomLikeApiException(ex: LikeApiException): ResponseEntity<ResponseToServerDto> {
        return ResponseEntity.ok().body(
            ResponseToServerDto(
                error = ServerErrorWithRfc7807(
                    type = null,
                    status = ex.errorCode.code,
                    title = ex.errorCode.title,
                    detail = ex.message,
                    instance = null
                ),
                data = null
            )
        )
    }

}