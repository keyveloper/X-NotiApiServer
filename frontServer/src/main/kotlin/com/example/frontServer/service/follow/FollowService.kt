package com.example.frontServer.service.follow

import com.example.frontServer.dto.user.UserSummaryDto
import com.example.frontServer.entity.Follow
import com.example.frontServer.exception.NotFoundEntityException
import com.example.frontServer.repository.follow.FollowRepository
import com.example.frontServer.repository.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun save(followingName: String, followerId: Long) {
        val userId = userRepository.findIdByUsername(followingName)
        if (userId != null) {
            followRepository.save(
                Follow(
                    followingId = userId,
                    followerId = followerId
                )
            )
        } else {
            throw NotFoundEntityException("can't find follow target id: $followingName")
        }
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