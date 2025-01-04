package com.example.frontServer.service.user

import com.example.frontServer.dto.auth.LoginRequest
import com.example.frontServer.dto.auth.LoginResult
import com.example.frontServer.security.JwtAuthenticationProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenProvider: JwtAuthenticationProvider,
    private val authenticationManger: AuthenticationManager,
) {
    private val logger = KotlinLogging.logger {}
    fun login(loginRequest: LoginRequest): LoginResult{
        val authentication: Authentication = authenticationManger.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        ) // check username and password
        logger.info { "authentication: $authentication" }

        return LoginResult(
            jwtToken = jwtTokenProvider.generateToken(authentication)
        )
    }
}