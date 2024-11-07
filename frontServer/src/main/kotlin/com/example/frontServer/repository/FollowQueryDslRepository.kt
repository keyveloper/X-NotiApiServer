package com.example.frontServer.repository

import com.example.frontServer.entity.User
import org.springframework.stereotype.Repository

@Repository
interface FollowQueryDslRepository {
    // following Users
    fun findFollowersByUsername(username: String): List<User>

    fun findFollowingsByUserId(username: String): List<User>

}