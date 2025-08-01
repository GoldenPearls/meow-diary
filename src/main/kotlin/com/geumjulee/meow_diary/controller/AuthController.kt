package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.LoginRequest
import com.geumjulee.meow_diary.dto.LoginResponse
import com.geumjulee.meow_diary.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService
) {
    
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val loginResult = userService.login(request.username, request.password)
        return ResponseEntity.ok(loginResult)
    }
    
    @PostMapping("/logout")
    fun logout(): ResponseEntity<Void> {
        // 실제로는 토큰을 무효화하는 로직이 필요
        return ResponseEntity.ok().build()
    }
    
    @GetMapping("/me")
    fun getCurrentUser(): ResponseEntity<Any> {
        // 현재 로그인한 사용자 정보 반환
        return ResponseEntity.ok(mapOf("message" to "인증된 사용자"))
    }
} 