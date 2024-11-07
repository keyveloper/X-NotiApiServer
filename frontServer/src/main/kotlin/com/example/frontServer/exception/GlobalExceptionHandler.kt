package com.example.frontServer.exception

import com.example.frontServer.dto.ResponseToClientDto
import com.example.frontServer.enum.ErrorCode
import io.jsonwebtoken.io.IOException
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.ResourceAccessException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handleIOException(ex: IOException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = ErrorCode.CONNECTION_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleParameterException(ex: MissingServletRequestParameterException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = ErrorCode.PARAMETER_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = ErrorCode.VALIDATION_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleCredentialsException(ex: ResourceAccessException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = ErrorCode.CREDENTIALS_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = ErrorCode.ACCESS_DENIED,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleCustomAppException(ex: ApplicationException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = ErrorCode.UNKNOWN_ID,
                data = null,
            )
        )
    }
}