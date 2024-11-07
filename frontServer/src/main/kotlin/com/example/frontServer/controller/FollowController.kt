package com.example.frontServer.controller

import com.example.frontServer.dto.UserSummaryDto
import com.example.frontServer.security.AuthUserDetails
import com.example.frontServer.service.FollowService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FollowController(
    private val followService: FollowService
) {

    @PostMapping("/follow")
    fun save(
        @RequestParam followingName: String
    ): ResponseEntity<Void> {
        val authentication = SecurityContextHolder.getContext().authentication
        val followerUser = authentication.principal as AuthUserDetails
        val followerId = followerUser.getUserId()

        followService.save(followingName, followerId)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/following/users")
    fun findFollowingUsers(
        @RequestParam username: String
    ): ResponseEntity<List<UserSummaryDto>> {
        val followingSummaries: List<UserSummaryDto> =
            followService.findFollowings(username)

        return ResponseEntity.ok().body(followingSummaries)
    }

    @GetMapping("/follower/users")
    fun findFollowerUsers(
        @RequestParam username: String
    ): ResponseEntity<List<UserSummaryDto>> {

        val followerSummaries: List<UserSummaryDto>? =
            followService.findFollowers(username)
        if (followerSummaries == null) {
            return ResponseEntity.badRequest().body(null)
        }

        return ResponseEntity.ok().body(followerSummaries)
    }
}