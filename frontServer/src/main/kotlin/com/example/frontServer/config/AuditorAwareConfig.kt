package com.example.frontServer.config

import com.example.frontServer.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

import java.util.*

@Configuration
class AuditorAwareConfig(
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger {}
    @Bean
    fun auditorProvider(): AuditorAware<com.example.frontServer.entity.User> {
        return AuditorAware<com.example.frontServer.entity.User> {
            val authentication: Authentication? = SecurityContextHolder.getContext().authentication

            if (authentication == null || !authentication.isAuthenticated) {
                logger.info {"No authenticated user found"}
                Optional.empty()
            } else {
                val principal = authentication.principal

                if (principal is User) {
                    logger.info { "Authenticated user: ${principal.username}" }  // user 객체로 캐스팅된 후 username 접근
                    Optional.of(userRepository.findByUsername(principal.username)!!)
                } else {
                    logger.info { "Principal is not of type User: $principal" }
                    Optional.empty()
                }
            }
        }
    }
}