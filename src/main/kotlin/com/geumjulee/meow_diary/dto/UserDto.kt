package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class UserRegistrationRequest(
    @field:NotBlank(message = "사용자명은 필수입니다")
    @field:Size(min = 2, max = 50, message = "사용자명은 2-50자 사이여야 합니다")
    val username: String,
    
    @field:NotBlank(message = "이메일은 필수입니다")
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    val email: String,
    
    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 6, max = 100, message = "비밀번호는 6-100자 사이여야 합니다")
    val password: String,
    
    val nickname: String? = null
)

data class UserLoginRequest(
    @field:NotBlank(message = "사용자명은 필수입니다")
    val username: String,
    
    @field:NotBlank(message = "비밀번호는 필수입니다")
    val password: String
)

data class UserUpdateRequest(
    val nickname: String? = null,
    val profileImageUrl: String? = null
)

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val nickname: String?,
    val profileImageUrl: String?,
    val isActive: Boolean,
    val role: UserRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                username = user.username,
                email = user.email,
                nickname = user.nickname,
                profileImageUrl = user.profileImageUrl,
                isActive = user.isActive,
                role = user.role,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
        }
    }
}

data class UserProfileResponse(
    val id: Long,
    val username: String,
    val nickname: String?,
    val profileImageUrl: String?,
    val catCount: Int,
    val postCount: Int,
    val createdAt: LocalDateTime
) 