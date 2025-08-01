package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    
    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody request: UserRegistrationRequest): ResponseEntity<UserResponse> {
        val user = userService.registerUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

    @PostMapping("/check-duplicate")
    fun checkDuplicate(@Valid @RequestBody request: DuplicateCheckRequest): ResponseEntity<DuplicateCheckResponse> {
        val result = userService.checkDuplicate(request)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/verify-email")
    fun verifyEmail(@Valid @RequestBody request: EmailVerificationRequest): ResponseEntity<EmailVerificationResponse> {
        val result = userService.verifyEmail(request.token)
        return ResponseEntity.ok(result)
    }
    
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }
    
    @GetMapping("/username/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<UserResponse> {
        val user = userService.getUserByUsername(username)
        return ResponseEntity.ok(user)
    }
    
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody request: UserUpdateRequest): ResponseEntity<UserResponse> {
        val user = userService.updateUser(id, request)
        return ResponseEntity.ok(user)
    }
    
    @DeleteMapping("/{id}")
    fun deactivateUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deactivateUser(id)
        return ResponseEntity.noContent().build()
    }
    
    @GetMapping("/active")
    fun getAllActiveUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.getAllActiveUsers()
        return ResponseEntity.ok(users)
    }
    
    @GetMapping("/role/{role}")
    fun getUsersByRole(@PathVariable role: String): ResponseEntity<List<UserResponse>> {
        val users = userService.getUsersByRole(role)
        return ResponseEntity.ok(users)
    }
} 