package com.example.frontServer.dto

import com.example.frontServer.annotaion.ValidPassword
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class LoginRequest(
    @field:NotEmpty
    @field: NotBlank
    @field:Pattern(
        regexp = "^[a-zA-Z0-9]+$",  // alphabet & number only !!
        message = "Username must not contain spaces or special characters"
    )
    val username: String,

    @field:NotEmpty
    @field:NotBlank
    @field:ValidPassword(message = "password must have Character, number and special Char at least one")
    val password: String,
)//
