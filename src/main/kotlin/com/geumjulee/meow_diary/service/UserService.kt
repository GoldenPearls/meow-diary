package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.UserRegistrationRequest
import com.geumjulee.meow_diary.dto.UserResponse
import com.geumjulee.meow_diary.dto.UserUpdateRequest
import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    
    fun registerUser(request: UserRegistrationRequest): UserResponse {
        // 중복 검사
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("이미 존재하는 사용자명입니다")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다")
        }
        
        val user = User().apply {
            username = request.username
            email = request.email
            password = passwordEncoder.encode(request.password)
            nickname = request.nickname
            role = UserRole.USER
            isActive = true
        }
        
        val savedUser = userRepository.save(user)
        return UserResponse.from(savedUser)
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
        
        request.nickname?.let { user.nickname = it }
        request.profileImageUrl?.let { user.profileImageUrl = it }
        
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