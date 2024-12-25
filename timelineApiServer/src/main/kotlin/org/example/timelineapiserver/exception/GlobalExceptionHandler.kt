package org.example.timelineapiserver.exception


import org.example.timelineapiserver.dto.error.MSAServerErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    // DB data Integrity Violations
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException):ResponseEntity<MSAServerErrorResponse> {
        return ResponseEntity.ok().body(
            MSAServerErrorResponse(
                errorCode = e.errorCode,
                errorDetails = e.errorDetails
            )
        )
    }
}