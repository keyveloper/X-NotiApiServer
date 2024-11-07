package com.example.frontServer.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


data class AuthUserDetails(
    private val userId: Long,
    private val username: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    fun getUserId(): Long {
        return userId
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
