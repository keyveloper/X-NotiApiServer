package com.example.frontServer.dto

import com.example.frontServer.entity.User

data class UserSummaryDto(
    val username: String,
    val userImg: String?,
) {
    companion object {
        fun of(user: User): UserSummaryDto {
            return UserSummaryDto(
                username = user.username,
                userImg = user.userImg,
            )
        }
    }
}
