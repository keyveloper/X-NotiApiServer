package com.example.frontServer.controller

import com.example.frontServer.dto.GetUserResponse
import com.example.frontServer.dto.ResponseToClientDto
import com.example.frontServer.dto.SignUpRequest
import com.example.frontServer.enum.ErrorCode
import com.example.frontServer.enum.SignUpStatus
import com.example.frontServer.service.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {
    private val logger = KotlinLogging.logger {}
    @PostMapping("/sign-up")
    fun signUp(
        @Valid @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<ResponseToClientDto> {
        val status: SignUpStatus = userService.signUp(signUpRequest)
        return when (status) {
            SignUpStatus.SUCCESS -> ResponseEntity.ok().body(
                ResponseToClientDto(
                    errorCode = null,
                    data = status.message
                )
            )

            SignUpStatus.DUPLICATED -> ResponseEntity.ok().body(
                ResponseToClientDto(
                    errorCode = ErrorCode.SAVE_FAILURE,
                    data = status.message
                )
            )
        }
    }

    @GetMapping("/user")
    fun findUserInfoById(
        @RequestParam loginId: String
    ): ResponseEntity<GetUserResponse> {
        val result = userService.findUserByLoginId(loginId)
        if (result != null) {
            return ResponseEntity.ok().body(GetUserResponse.of(result))
        }
        return ResponseEntity.notFound().build()
    }//
}