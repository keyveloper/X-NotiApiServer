package com.example.frontServer.exception

import com.example.frontServer.dto.ResponseToClientDto
import com.example.frontServer.enum.FrontServerError
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
                errorCode = FrontServerError.CONNECTION_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleParameterException(ex: MissingServletRequestParameterException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = FrontServerError.PARAMETER_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = FrontServerError.VALIDATION_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleCredentialsException(ex: ResourceAccessException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = FrontServerError.CREDENTIALS_ERROR,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = FrontServerError.ACCESS_DENIED,
                data = null
            )
        )
    }

    @ExceptionHandler
    fun handleCustomAppException(ex: ApplicationException): ResponseEntity<ResponseToClientDto> {
        return ResponseEntity.ok().body(
            ResponseToClientDto(
                errorCode = FrontServerError.UNKNOWN_ID,
                data = null,
            )
        )
    }
}