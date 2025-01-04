package com.example.frontServer.service.user

import com.example.frontServer.entity.User
import com.example.frontServer.repository.user.UserRepository
import com.example.frontServer.repository.user.UserRoleRepository
import com.example.frontServer.security.AuthUserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
) : UserDetailsService{

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username)
            ?:throw UsernameNotFoundException("User not found with username: $username")

        val authorities = userRoleRepository.findRolesByUsername(username)
            .map { role -> SimpleGrantedAuthority(role.name) }
            .toSet()

        return AuthUserDetails(
            user.id!!,
            user.username,
            user.password,
            authorities
        )
    }
}