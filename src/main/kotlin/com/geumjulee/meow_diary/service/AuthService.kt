package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService,
    private val emailService: EmailService,
    private val restTemplate: RestTemplate = RestTemplate(),
    private val objectMapper: ObjectMapper = ObjectMapper()
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
    
    // 소셜 로그인 처리
    fun socialLogin(request: SocialLoginRequest): LoginResponse {
        val socialUserInfo = when (request.provider.lowercase()) {
            "google" -> getGoogleUserInfo(request.accessToken, request.idToken)
            "naver" -> getNaverUserInfo(request.accessToken)
            "apple" -> getAppleUserInfo(request.idToken ?: request.accessToken)
            else -> throw IllegalArgumentException("지원하지 않는 소셜 로그인 타입입니다")
        }
        
        // 기존 사용자 찾기 또는 새 사용자 생성
        val user = findOrCreateSocialUser(socialUserInfo)
        
        // JWT 토큰 생성
        val accessToken = jwtTokenService.generateAccessToken(user.id, user.username, user.email)
        val refreshToken = jwtTokenService.generateRefreshToken(user.id, user.username)
        
        // 로그인 시간 업데이트
        user.lastLoginAt = LocalDateTime.now()
        userRepository.save(user)
        
        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
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
    
    private fun getGoogleUserInfo(accessToken: String, idToken: String?): SocialUserInfo {
        return try {
            val headers = HttpHeaders()
            headers.setBearerAuth(accessToken)
            val entity = HttpEntity<String>(headers)
            
            val response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                entity,
                String::class.java
            )
            
            val userInfo = objectMapper.readTree(response.body)
            
            SocialUserInfo(
                id = userInfo.get("id").asText(),
                email = userInfo.get("email")?.asText(),
                name = userInfo.get("name")?.asText(),
                nickname = userInfo.get("name")?.asText(),
                profileImageUrl = userInfo.get("picture")?.asText(),
                provider = SocialLoginType.GOOGLE
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("Google 사용자 정보를 가져올 수 없습니다: ${e.message}")
        }
    }
    
    private fun getNaverUserInfo(accessToken: String): SocialUserInfo {
        return try {
            val headers = HttpHeaders()
            headers.setBearerAuth(accessToken)
            val entity = HttpEntity<String>(headers)
            
            val response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                entity,
                String::class.java
            )
            
            val responseBody = objectMapper.readTree(response.body)
            val userInfo = responseBody.get("response")
            
            SocialUserInfo(
                id = userInfo.get("id").asText(),
                email = userInfo.get("email")?.asText(),
                name = userInfo.get("name")?.asText(),
                nickname = userInfo.get("nickname")?.asText(),
                profileImageUrl = userInfo.get("profile_image")?.asText(),
                provider = SocialLoginType.NAVER
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("네이버 사용자 정보를 가져올 수 없습니다: ${e.message}")
        }
    }
    
    private fun getAppleUserInfo(idToken: String): SocialUserInfo {
        // Apple ID Token은 JWT 형태이므로 디코딩하여 사용자 정보 추출
        // 실제 구현에서는 Apple의 공개 키로 서명 검증이 필요
        return try {
            val parts = idToken.split(".")
            if (parts.size != 3) {
                throw IllegalArgumentException("잘못된 Apple ID Token 형식입니다")
            }
            
            val payload = String(Base64.getDecoder().decode(parts[1]))
            val userInfo = objectMapper.readTree(payload)
            
            SocialUserInfo(
                id = userInfo.get("sub").asText(),
                email = userInfo.get("email")?.asText(),
                name = null, // Apple은 이름 정보를 제공하지 않을 수 있음
                nickname = userInfo.get("email")?.asText()?.split("@")?.get(0),
                profileImageUrl = null,
                provider = SocialLoginType.APPLE
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("Apple 사용자 정보를 가져올 수 없습니다: ${e.message}")
        }
    }
    
    private fun findOrCreateSocialUser(socialUserInfo: SocialUserInfo): User {
        // 이메일로 기존 사용자 찾기
        socialUserInfo.email?.let { email ->
            val existingUser = userRepository.findByEmail(email)
            if (existingUser.isPresent) {
                return existingUser.get()
            }
        }
        
        // 새 사용자 생성
        val user = User()
        user.username = generateUniqueUsername(socialUserInfo)
        user.password = passwordEncoder.encode(UUID.randomUUID().toString()) // 임시 비밀번호
        user.nickname = generateUniqueNickname(socialUserInfo)
        user.firstName = extractFirstName(socialUserInfo.name)
        user.lastName = extractLastName(socialUserInfo.name)
        user.email = socialUserInfo.email ?: ""
        user.role = UserRole.USER
        user.isActive = true
        user.emailVerified = true // 소셜 로그인은 이메일 인증된 것으로 간주
        
        return userRepository.save(user)
    }
    
    private fun generateUniqueUsername(socialUserInfo: SocialUserInfo): String {
        val baseUsername = "${socialUserInfo.provider.name.lowercase()}_${socialUserInfo.id}"
        var username = baseUsername
        var counter = 1
        
        while (userRepository.existsByUsername(username)) {
            username = "${baseUsername}_$counter"
            counter++
        }
        
        return username
    }
    
    private fun generateUniqueNickname(socialUserInfo: SocialUserInfo): String {
        val baseNickname = socialUserInfo.nickname 
            ?: socialUserInfo.name 
            ?: socialUserInfo.email?.split("@")?.get(0) 
            ?: "사용자"
        
        var nickname = baseNickname
        var counter = 1
        
        while (userRepository.existsByNickname(nickname)) {
            nickname = "${baseNickname}$counter"
            counter++
        }
        
        return nickname
    }
    
    private fun extractFirstName(fullName: String?): String {
        return fullName?.split(" ")?.firstOrNull() ?: "이름"
    }
    
    private fun extractLastName(fullName: String?): String {
        val parts = fullName?.split(" ") ?: return "성"
        return if (parts.size > 1) parts.drop(1).joinToString(" ") else "성"
    }
}