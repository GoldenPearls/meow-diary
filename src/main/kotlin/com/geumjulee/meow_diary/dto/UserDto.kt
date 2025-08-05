package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class UserRegistrationRequest(
    @field:NotBlank(message = "아이디는 필수입니다")
    @field:Size(min = 3, max = 20, message = "아이디는 3-20자 사이여야 합니다")
    val username: String,
    
    @field:NotBlank(message = "비밀번호는 필수입니다")
    @field:Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
    val password: String,
    
    @field:NotBlank(message = "닉네임은 필수입니다")
    @field:Size(min = 2, max = 20, message = "닉네임은 2-20자 사이여야 합니다")
    val nickname: String,
    
    @field:NotBlank(message = "이름은 필수입니다")
    val firstName: String,
    
    @field:NotBlank(message = "성은 필수입니다")
    val lastName: String,
    
    @field:Email(message = "올바른 이메일 형식이어야 합니다")
    val email: String,
    
    val phone: String? = null,
    val address: String? = null
)



data class UserResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val address: String?,
    val isActive: Boolean,
    val emailVerified: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                username = user.username,
                nickname = user.nickname,
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                phone = user.phone,
                address = user.address,
                isActive = user.isActive,
                emailVerified = user.emailVerified,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt
            )
        }
    }
}

data class UserUpdateRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null
)

data class UserProfileResponse(
    val id: Long,
    val username: String,
    val nickname: String?,
    val profileImageUrl: String?,
    val catCount: Int,
    val postCount: Int,
    val createdAt: LocalDateTime
)

data class EmailVerificationResponse(
    val success: Boolean,
    val message: String
) 