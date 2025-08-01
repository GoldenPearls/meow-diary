package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.*
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
        // 중복 확인
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("이미 사용 중인 아이디입니다")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다")
        }
        if (userRepository.existsByNickname(request.nickname)) {
            throw IllegalArgumentException("이미 사용 중인 닉네임입니다")
        }

        val emailVerificationToken = UUID.randomUUID().toString()

        val user = User()
        user.username = request.username
        user.email = request.email
        user.password = passwordEncoder.encode(request.password)
        user.nickname = request.nickname
        user.firstName = request.firstName
        user.lastName = request.lastName
        user.phone = request.phone
        user.address = request.address
        user.role = UserRole.USER
        user.isActive = true
        user.emailVerified = false
        user.emailVerificationToken = emailVerificationToken

        val savedUser = userRepository.save(user)
        
        // TODO: 이메일 인증 메일 발송
        // sendVerificationEmail(savedUser.email, emailVerificationToken)
        
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

        val token = UUID.randomUUID().toString()
        
        return LoginResponse(
            token = token,
            userId = user.id,
            username = user.username,
            nickname = user.nickname,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            emailVerified = user.emailVerified
        )
    }

    fun checkDuplicate(request: DuplicateCheckRequest): DuplicateCheckResponse {
        val isDuplicate = when (request.type.lowercase()) {
            "username" -> userRepository.existsByUsername(request.value)
            "email" -> userRepository.existsByEmail(request.value)
            "nickname" -> userRepository.existsByNickname(request.value)
            else -> throw IllegalArgumentException("잘못된 확인 타입입니다")
        }

        val message = if (isDuplicate) {
            when (request.type.lowercase()) {
                "username" -> "이미 사용 중인 아이디입니다"
                "email" -> "이미 사용 중인 이메일입니다"
                "nickname" -> "이미 사용 중인 닉네임입니다"
                else -> "이미 사용 중입니다"
            }
        } else {
            when (request.type.lowercase()) {
                "username" -> "사용 가능한 아이디입니다"
                "email" -> "사용 가능한 이메일입니다"
                "nickname" -> "사용 가능한 닉네임입니다"
                else -> "사용 가능합니다"
            }
        }

        return DuplicateCheckResponse(isDuplicate, message)
    }

    fun verifyEmail(token: String): EmailVerificationResponse {
        val user = userRepository.findByEmailVerificationToken(token)
            ?: return EmailVerificationResponse(false, "유효하지 않은 인증 토큰입니다")

        user.emailVerified = true
        user.emailVerificationToken = null
        userRepository.save(user)

        return EmailVerificationResponse(true, "이메일 인증이 완료되었습니다")
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