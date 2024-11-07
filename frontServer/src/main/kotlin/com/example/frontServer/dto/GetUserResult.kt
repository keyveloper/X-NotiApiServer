package com.example.frontServer.dto

import com.example.frontServer.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

data class GetUserResult(
    val email: String,
    val username: String,
    val introduction: String?,
    val birthday: LocalDate?,
    val firstMadeDate: LocalDateTime,
    val country: String?,
    ) {
    companion object {
        fun of(user: User): GetUserResult {
            return GetUserResult(
                email = user.email,
                username = user.username,
                introduction = user.introduction,
                birthday = user.birthday,
                firstMadeDate = user.firstMadeDate!!,
                country = user.country,
            )
        }
    }

}