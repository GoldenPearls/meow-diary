package com.geumjulee.meow_diary.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtTokenService {
    
    @Value("\${app.jwt.secret:MeowDiary2024SecretKeyForJWTTokenGenerationAndValidation}")
    private lateinit var jwtSecret: String
    
    @Value("\${app.jwt.expiration:86400000}") // 24시간
    private var jwtExpirationMs: Long = 86400000
    
    @Value("\${app.jwt.refresh-expiration:604800000}") // 7일
    private var refreshExpirationMs: Long = 604800000
    
    private val key: Key by lazy { Keys.hmacShaKeyFor(jwtSecret.toByteArray()) }
    
    fun generateAccessToken(userId: Long, username: String, email: String): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("userId", userId)
            .claim("email", email)
            .claim("type", "access")
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
    
    fun generateRefreshToken(userId: Long, username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("userId", userId)
            .claim("type", "refresh")
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + refreshExpirationMs))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
    
    fun getUsernameFromToken(token: String): String {
        return getClaims(token).subject
    }
    
    fun getUserIdFromToken(token: String): Long {
        return getClaims(token).get("userId", Integer::class.java).toLong()
    }
    
    fun getEmailFromToken(token: String): String? {
        return getClaims(token).get("email", String::class.java)
    }
    
    fun getTokenType(token: String): String {
        return getClaims(token).get("type", String::class.java)
    }
    
    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: JwtException) {
            return false
        }
    }
    
    fun isTokenExpired(token: String): Boolean {
        return try {
            getClaims(token).expiration.before(Date())
        } catch (e: JwtException) {
            true
        }
    }
    
    fun getExpirationFromToken(token: String): Date? {
        return try {
            getClaims(token).expiration
        } catch (e: JwtException) {
            null
        }
    }
    
    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}