package com.example.frontServer.repository

import com.example.frontServer.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun existsUserByUsername(username: String): Boolean

    fun existsUserByEmail(email: String): Boolean

    @Query("select u.id from User u where u.username = :username")
    fun findIdByUsername(username: String): Long?
}//