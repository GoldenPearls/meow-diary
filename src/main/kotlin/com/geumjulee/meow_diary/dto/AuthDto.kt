package com.geumjulee.meow_diary.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

// 로그인 요청
data class LoginRequest(
    @field:NotBlank(message = "아이디는 필수입니다")
    val username: String,
    
    @field:NotBlank(message = "비밀번호는 필수입니다")
    val password: String,
    
    val rememberMe: Boolean = false
)

// 로그인 응답
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long, // 초 단위
    val user: UserSummary
)

// 회원가입 요청
data class RegisterRequest(
    @field:NotBlank(message = "아이디는 필수입니다")
    @field:Size(min = 3, max = 20, message = "아이디는 3-20자 사이여야 합니다")
    @field:Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문, 숫자, 언더스코어만 사용 가능합니다")
    val username: String,
    
    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]+$",
        message = "비밀번호는 대소문자, 숫자, 특수문자를 각각 최소 1개씩 포함해야 합니다"
    )
    val password: String,
    
    @field:NotBlank(message = "비밀번호 확인은 필수입니다")
    val confirmPassword: String,
    
    @field:NotBlank(message = "닉네임은 필수입니다")
    @field:Size(min = 2, max = 20, message = "닉네임은 2-20자 사이여야 합니다")
    val nickname: String,
    
    @field:NotBlank(message = "이름은 필수입니다")
    val firstName: String,
    
    @field:NotBlank(message = "성은 필수입니다")
    val lastName: String,
    
    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이어야 합니다")
    val email: String,
    
    @field:Pattern(regexp = "^(010-\\d{4}-\\d{4})?$", message = "전화번호는 010-0000-0000 형식이어야 합니다")
    val phone: String? = null,
    
    val address: String? = null,
    
    @field:NotBlank(message = "이메일 인증번호는 필수입니다")
    @field:Size(min = 6, max = 6, message = "인증번호는 6자리여야 합니다")
    val emailVerificationCode: String
) {
    fun isPasswordConfirmed(): Boolean = password == confirmPassword
}

// 이메일 인증 요청
data class EmailVerificationRequest(
    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이어야 합니다")
    val email: String
)

// 이메일 인증번호 확인 요청
data class EmailVerificationCheckRequest(
    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이어야 합니다")
    val email: String,
    
    @field:NotBlank(message = "인증번호는 필수입니다")
    @field:Size(min = 6, max = 6, message = "인증번호는 6자리여야 합니다")
    val verificationCode: String
)

// 토큰 갱신 요청
data class RefreshTokenRequest(
    @field:NotBlank(message = "리프레시 토큰은 필수입니다")
    val refreshToken: String
)

// 사용자 요약 정보
data class UserSummary(
    val id: Long,
    val username: String,
    val nickname: String,
    val email: String,
    val emailVerified: Boolean,
    val firstName: String,
    val lastName: String
)

// 중복 확인 요청
data class DuplicateCheckRequest(
    @field:NotBlank(message = "확인할 값은 필수입니다")
    val value: String,
    
    @field:NotBlank(message = "확인할 타입은 필수입니다")
    val type: String // "username", "email", "nickname"
)

// 중복 확인 응답
data class DuplicateCheckResponse(
    val isDuplicate: Boolean,
    val message: String
)

// 비밀번호 변경 요청
data class PasswordChangeRequest(
    @field:NotBlank(message = "현재 비밀번호는 필수입니다")
    val currentPassword: String,
    
    @field:NotBlank(message = "새 비밀번호는 필수입니다")
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]+$",
        message = "비밀번호는 대소문자, 숫자, 특수문자를 각각 최소 1개씩 포함해야 합니다"
    )
    val newPassword: String,
    
    @field:NotBlank(message = "새 비밀번호 확인은 필수입니다")
    val confirmNewPassword: String
) {
    fun isNewPasswordConfirmed(): Boolean = newPassword == confirmNewPassword
}

// 소셜 로그인 타입
enum class SocialLoginType {
    GOOGLE, NAVER, APPLE
}

// 소셜 로그인 요청
data class SocialLoginRequest(
    @field:NotBlank(message = "소셜 로그인 타입은 필수입니다")
    val provider: String, // "google", "naver", "apple"
    
    @field:NotBlank(message = "액세스 토큰은 필수입니다")
    val accessToken: String,
    
    val idToken: String? = null, // Google/Apple ID Token (선택적)
    
    val email: String? = null,
    val name: String? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null
)

// 소셜 로그인 사용자 정보 (외부 API에서 받아온 정보)
data class SocialUserInfo(
    val id: String,
    val email: String?,
    val name: String?,
    val nickname: String?,
    val profileImageUrl: String?,
    val provider: SocialLoginType
)