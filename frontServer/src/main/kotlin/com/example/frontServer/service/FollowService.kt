package com.example.frontServer.service

import com.example.frontServer.dto.UserSummaryDto
import com.example.frontServer.entity.Follow
import com.example.frontServer.repository.FollowRepository
import com.example.frontServer.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun save(followingName: String, followerId: Long): String {
        val userId = userRepository.findIdByUsername(followingName)
        if (userId != null) {
            followRepository.save(
                Follow(
                    followingId = userId,
                    followerId = followerId
                )
            )
            return "save success"
        }
        return "Invalid Username"
    }

    @Transactional
    fun findFollowers(
        username: String
    ): List<UserSummaryDto>? {
        return followRepository.findFollowersByUsername(username)
            .map { UserSummaryDto.of(it) }

        // exception? unknown username?
    }

    @Transactional
    fun findFollowings(
        username: String,
    ): List<UserSummaryDto> {
        return followRepository.findFollowingsByUserId(username)
            .map { UserSummaryDto.of(it) }
    }
}