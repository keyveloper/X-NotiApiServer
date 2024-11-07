package com.example.frontServer.service

import com.example.frontServer.dto.LoginRequest
import com.example.frontServer.security.JwtAuthenticationProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenProvider: JwtAuthenticationProvider,
    private val authenticationManger: AuthenticationManager,
) {
    private val logger = KotlinLogging.logger {}
    fun login(loginInfo: LoginRequest): String? {
        return try {
            val authentication: Authentication = authenticationManger.authenticate(
                UsernamePasswordAuthenticationToken(loginInfo.username, loginInfo.password)
            ) // check username and password
            logger.info { "authentication: $authentication" }

            jwtTokenProvider.generateToken(authentication)
        } catch (e: AuthenticationException) {
            throw RuntimeException("${e.message}")
        }
    }
}