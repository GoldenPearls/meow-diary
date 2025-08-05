package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.service.AuthService
import com.geumjulee.meow_diary.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {
    
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        return try {
            val loginResult = authService.login(request)
            ResponseEntity.ok(ApiResponse.success(loginResult, "로그인이 완료되었습니다"))
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<LoginResponse>(e.message ?: "로그인에 실패했습니다"))
        }
    }
    
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<ApiResponse<UserResponse>> {
        return try {
            val userResponse = authService.register(request)
            ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userResponse, "회원가입이 완료되었습니다"))
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<UserResponse>(e.message ?: "회원가입에 실패했습니다"))
        }
    }
    
    @PostMapping("/refresh")
    fun refreshToken(@Valid @RequestBody request: RefreshTokenRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        return try {
            val loginResult = authService.refreshToken(request)
            ResponseEntity.ok(ApiResponse.success(loginResult, "토큰이 갱신되었습니다"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error<LoginResponse>(e.message ?: "토큰 갱신에 실패했습니다"))
        }
    }
    
    @PostMapping("/send-email-verification")
    fun sendEmailVerification(@Valid @RequestBody request: EmailVerificationRequest): ResponseEntity<ApiResponse<String>> {
        return try {
            authService.sendEmailVerification(request)
            ResponseEntity.ok(ApiResponse.success("", "인증번호가 이메일로 발송되었습니다"))
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<String>(e.message ?: "이메일 발송에 실패했습니다"))
        }
    }
    
    @PostMapping("/verify-email")
    fun verifyEmail(@Valid @RequestBody request: EmailVerificationCheckRequest): ResponseEntity<ApiResponse<Boolean>> {
        return try {
            val isVerified = authService.checkEmailVerification(request)
            if (isVerified) {
                ResponseEntity.ok(ApiResponse.success(true, "이메일 인증이 완료되었습니다"))
            } else {
                ResponseEntity.badRequest()
                    .body(ApiResponse.error<Boolean>("인증번호가 올바르지 않거나 만료되었습니다"))
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<Boolean>(e.message ?: "이메일 인증에 실패했습니다"))
        }
    }
    
    @PostMapping("/check-duplicate")
    fun checkDuplicate(@Valid @RequestBody request: DuplicateCheckRequest): ResponseEntity<ApiResponse<DuplicateCheckResponse>> {
        return try {
            val result = authService.checkDuplicate(request)
            ResponseEntity.ok(ApiResponse.success(result))
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<DuplicateCheckResponse>(e.message ?: "중복 확인에 실패했습니다"))
        }
    }
    
    @PostMapping("/change-password")
    fun changePassword(
        @RequestHeader("Authorization") authorization: String,
        @Valid @RequestBody request: PasswordChangeRequest
    ): ResponseEntity<ApiResponse<Boolean>> {
        return try {
            // JWT 토큰에서 사용자 ID 추출 (추후 구현)
            val userId = 1L // 임시
            val result = authService.changePassword(userId, request)
            ResponseEntity.ok(ApiResponse.success(result, "비밀번호가 변경되었습니다"))
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<Boolean>(e.message ?: "비밀번호 변경에 실패했습니다"))
        }
    }
    
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authorization: String): ResponseEntity<ApiResponse<String>> {
        return try {
            val token = authorization.replace("Bearer ", "")
            authService.logout(token)
            ResponseEntity.ok(ApiResponse.success("", "로그아웃이 완료되었습니다"))
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<String>(e.message ?: "로그아웃에 실패했습니다"))
        }
    }
    
    @GetMapping("/me")
    fun getCurrentUser(@RequestHeader("Authorization") authorization: String): ResponseEntity<ApiResponse<UserResponse>> {
        return try {
            // JWT 토큰에서 사용자 ID 추출 (추후 구현)
            val userId = 1L // 임시
            val user = userService.getUserById(userId)
            ResponseEntity.ok(ApiResponse.success(user))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error<UserResponse>(e.message ?: "인증이 필요합니다"))
        }
    }
    
    @PostMapping("/social-login")
    fun socialLogin(@Valid @RequestBody request: SocialLoginRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        return try {
            val loginResult = authService.socialLogin(request)
            ResponseEntity.ok(ApiResponse.success(loginResult, "소셜 로그인이 완료되었습니다"))
        } catch (e: Exception) {
            ResponseEntity.badRequest()
                .body(ApiResponse.error<LoginResponse>(e.message ?: "소셜 로그인에 실패했습니다"))
        }
    }
} 