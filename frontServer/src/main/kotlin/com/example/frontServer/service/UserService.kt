package com.example.frontServer.service

import com.example.frontServer.dto.GetUserResult
import com.example.frontServer.dto.SignUpRequest
import com.example.frontServer.entity.User
import com.example.frontServer.entity.UserRole
import com.example.frontServer.enum.SignUpStatus
import com.example.frontServer.repository.UserRepository
import com.example.frontServer.repository.UserRoleRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun signUp(request: SignUpRequest): SignUpStatus {
        if (userRepository.existsUserByUsername(request.username) ||
            userRepository.existsUserByEmail(request.email)) {
            return SignUpStatus.DUPLICATED
        }

        val user = User(
            email = request.email,
            username = request.username,
            password = passwordEncoder.encode(request.password),
            introduction = request.introduction,
            birthday = request.birthday,
            country = request.country
        )
        userRepository.save(user)

        userRoleRepository.save(
            UserRole(
                user = user.id!!,
                role = request.role.ordinal.toLong()
            )
        )
        return SignUpStatus.SUCCESS
    }

    fun findUserByLoginId(loginId: String): GetUserResult? {
        val user = userRepository.findByUsername(loginId)
        return user?.let{ GetUserResult.of(user) }
    }//
}