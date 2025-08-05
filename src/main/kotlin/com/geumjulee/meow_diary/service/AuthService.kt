package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService,
    private val emailService: EmailService
) {
    
    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username)
            .orElseThrow { IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다") }
        
        if (!user.isActive) {
            throw IllegalArgumentException("비활성화된 계정입니다")
        }
        
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다")
        }
        
        // JWT 토큰 생성
        val accessToken = jwtTokenService.generateAccessToken(user.id, user.username, user.email)
        val refreshToken = jwtTokenService.generateRefreshToken(user.id, user.username)
        
        // 로그인 시간 업데이트
        user.lastLoginAt = LocalDateTime.now()
        userRepository.save(user)
        
        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = 86400, // 24시간
            user = UserSummary(
                id = user.id,
                username = user.username,
                nickname = user.nickname,
                email = user.email,
                emailVerified = user.emailVerified,
                firstName = user.firstName,
                lastName = user.lastName
            )
        )
    }
    
    fun register(request: RegisterRequest): UserResponse {
        // 비밀번호 확인
        if (!request.isPasswordConfirmed()) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다")
        }
        
        // 중복 확인
        validateUserUniqueness(request.username, request.email, request.nickname)
        
        // 이메일 인증 확인
        if (!emailService.verifyEmailCode(request.email, request.emailVerificationCode)) {
            throw IllegalArgumentException("이메일 인증번호가 유효하지 않습니다")
        }
        
        val user = User()
        user.username = request.username
        user.password = passwordEncoder.encode(request.password)
        user.nickname = request.nickname
        user.firstName = request.firstName
        user.lastName = request.lastName
        user.email = request.email
        user.phone = request.phone
        user.address = request.address
        user.role = UserRole.USER
        user.isActive = true
        user.emailVerified = true // 인증 완료
        
        val savedUser = userRepository.save(user)
        return UserResponse.from(savedUser)
    }
    
    fun refreshToken(request: RefreshTokenRequest): LoginResponse {
        if (!jwtTokenService.validateToken(request.refreshToken)) {
            throw IllegalArgumentException("유효하지 않은 리프레시 토큰입니다")
        }
        
        if (jwtTokenService.getTokenType(request.refreshToken) != "refresh") {
            throw IllegalArgumentException("리프레시 토큰이 아닙니다")
        }
        
        val username = jwtTokenService.getUsernameFromToken(request.refreshToken)
        
        val user = userRepository.findByUsername(username)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        // 새 토큰 생성
        val newAccessToken = jwtTokenService.generateAccessToken(user.id, user.username, user.email)
        val newRefreshToken = jwtTokenService.generateRefreshToken(user.id, user.username)
        
        return LoginResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            expiresIn = 86400,
            user = UserSummary(
                id = user.id,
                username = user.username,
                nickname = user.nickname,
                email = user.email,
                emailVerified = user.emailVerified,
                firstName = user.firstName,
                lastName = user.lastName
            )
        )
    }
    
    fun sendEmailVerification(request: EmailVerificationRequest): String {
        // 이미 등록된 이메일인지 확인
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 등록된 이메일입니다")
        }
        
        return emailService.sendVerificationEmail(request.email)
    }
    
    fun checkEmailVerification(request: EmailVerificationCheckRequest): Boolean {
        return emailService.verifyEmailCode(request.email, request.verificationCode)
    }
    
    fun changePassword(userId: Long, request: PasswordChangeRequest): Boolean {
        if (!request.isNewPasswordConfirmed()) {
            throw IllegalArgumentException("새 비밀번호가 일치하지 않습니다")
        }
        
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        if (!passwordEncoder.matches(request.currentPassword, user.password)) {
            throw IllegalArgumentException("현재 비밀번호가 일치하지 않습니다")
        }
        
        user.password = passwordEncoder.encode(request.newPassword)
        userRepository.save(user)
        
        return true
    }
    
    fun checkDuplicate(request: DuplicateCheckRequest): DuplicateCheckResponse {
        val isDuplicate = when (request.type.lowercase()) {
            "username" -> userRepository.existsByUsername(request.value)
            "email" -> userRepository.existsByEmail(request.value)
            "nickname" -> userRepository.existsByNickname(request.value)
            else -> throw IllegalArgumentException("지원하지 않는 확인 타입입니다")
        }
        
        val message = if (isDuplicate) {
            when (request.type.lowercase()) {
                "username" -> "이미 사용 중인 아이디입니다"
                "email" -> "이미 사용 중인 이메일입니다"
                "nickname" -> "이미 사용 중인 닉네임입니다"
                else -> "중복된 값입니다"
            }
        } else {
            "사용 가능합니다"
        }
        
        return DuplicateCheckResponse(isDuplicate, message)
    }
    
    fun logout(token: String) {
        // JWT 토큰을 블랙리스트에 추가하는 로직 (Redis 등 사용)
        // 현재는 클라이언트에서 토큰 삭제로 처리
    }
    
    private fun validateUserUniqueness(username: String, email: String, nickname: String) {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("이미 사용 중인 아이디입니다")
        }
        
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다")
        }
        
        if (userRepository.existsByNickname(nickname)) {
            throw IllegalArgumentException("이미 사용 중인 닉네임입니다")
        }
    }
}