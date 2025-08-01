package com.geumjulee.meow_diary.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor : HandlerInterceptor {
    
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // API 요청에 대해서만 인증 체크
        if (request.requestURI.startsWith("/api/")) {
            val authHeader = request.getHeader("Authorization")
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return false
            }
            
            // 간단한 토큰 검증 (실제로는 JWT 토큰 검증 필요)
            val token = authHeader.substring(7)
            if (token.isBlank()) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return false
            }
        }
        
        return true
    }
} 