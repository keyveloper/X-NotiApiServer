package com.example.frontServer.controller

import com.example.frontServer.dto.LoginRequest
import com.example.frontServer.service.AuthService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {
    private val logger = KotlinLogging.logger {}
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody loginInfo: LoginRequest
    ): ResponseEntity<String> {
        val token : String? = authService.login(loginInfo)
        return ResponseEntity.ok().body(token)
    }
}