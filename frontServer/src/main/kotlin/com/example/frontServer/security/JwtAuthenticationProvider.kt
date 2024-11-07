package com.example.frontServer.security

import com.example.frontServer.service.UserDetailsServiceImpl
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtAuthenticationProvider(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val jwtExpirationMs: Long,
    private val userDetailsService : UserDetailsServiceImpl
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    private val logger = KotlinLogging.logger {}

    // JWT 생성 메서드
    fun generateToken(authentication: Authentication): String {
        logger.info { "start generate token method! " }
        val usePrincipal = authentication.principal as AuthUserDetails
        logger.info {"userPrincipal: ${usePrincipal}"}
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationMs)

        return Jwts.builder()
            .setSubject(usePrincipal.username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserFromToken(token: String): UserDetails {
        val username: String = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
        return userDetailsService.loadUserByUsername(username)
    }


    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            // 예외 처리 다시
            false
        }
    }
}//