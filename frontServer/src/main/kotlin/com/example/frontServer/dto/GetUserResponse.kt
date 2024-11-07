package com.example.frontServer.dto

import java.time.LocalDate
import java.time.LocalDateTime
//
data class GetUserResponse(
    val email: String,
    val username: String,
    val introduction: String?,
    val birthday: LocalDate?,
    val accountCreatingDay: LocalDateTime,
    val country: String?,
) {
    companion object {
        fun of(request: GetUserResult): GetUserResponse {
            return GetUserResponse(
                email = request.email,
                username = request.username,
                introduction = request.introduction,
                birthday = request.birthday,
                accountCreatingDay = request.firstMadeDate,
                country = request.country,
            )
        }
    }
}