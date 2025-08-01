package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.LoginRequest
import com.geumjulee.meow_diary.dto.LoginResponse
import com.geumjulee.meow_diary.dto.UserRegistrationRequest
import com.geumjulee.meow_diary.dto.UserResponse
import com.geumjulee.meow_diary.dto.UserUpdateRequest
import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    
    fun registerUser(request: UserRegistrationRequest): UserResponse {
        // 중복 검사
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("이미 존재하는 아이디입니다")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다")
        }
        
        val user = User().apply {
            username = request.username
            email = request.email
            password = passwordEncoder.encode(request.password)
            firstName = request.firstName
            lastName = request.lastName
            phone = request.phone
            address = request.address
            role = UserRole.USER
            isActive = true
        }
        
        val savedUser = userRepository.save(user)
        return UserResponse.from(savedUser)
    }
    
    fun login(username: String, password: String): LoginResponse {
        val user = userRepository.findByUsername(username)
            .orElseThrow { IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다") }
        
        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다")
        }
        
        if (!user.isActive) {
            throw IllegalArgumentException("비활성화된 계정입니다")
        }
        
        // 간단한 토큰 생성 (실제로는 JWT 사용 권장)
        val token = generateSimpleToken()
        
        return LoginResponse(
            token = token,
            userId = user.id,
            username = user.username,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email
        )
    }
    
    private fun generateSimpleToken(): String {
        return UUID.randomUUID().toString()
    }
    
    @Transactional(readOnly = true)
    fun getUserById(id: Long): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        return UserResponse.from(user)
    }
    
    @Transactional(readOnly = true)
    fun getUserByUsername(username: String): UserResponse {
        val user = userRepository.findByUsername(username)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        return UserResponse.from(user)
    }
    
    fun updateUser(id: Long, request: UserUpdateRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        request.firstName?.let { user.firstName = it }
        request.lastName?.let { user.lastName = it }
        request.email?.let { user.email = it }
        request.phone?.let { user.phone = it }
        request.address?.let { user.address = it }
        
        val updatedUser = userRepository.save(user)
        return UserResponse.from(updatedUser)
    }
    
    fun deactivateUser(id: Long) {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        user.isActive = false
        userRepository.save(user)
    }
    
    @Transactional(readOnly = true)
    fun getAllActiveUsers(): List<UserResponse> {
        return userRepository.findAllActiveUsers().map { UserResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getUsersByRole(role: String): List<UserResponse> {
        return userRepository.findByRole(role).map { UserResponse.from(it) }
    }
} 