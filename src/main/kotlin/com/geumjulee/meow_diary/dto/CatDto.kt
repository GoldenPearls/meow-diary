package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.Cat
import com.geumjulee.meow_diary.entity.CatGender
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalDateTime

data class CatCreateRequest(
    @field:NotBlank(message = "고양이 이름은 필수입니다")
    val name: String,
    
    val breed: String? = null,
    val color: String? = null,
    val birthDate: LocalDate? = null,
    val weight: Double? = null,
    val gender: CatGender? = null,
    val isNeutered: Boolean = false,
    val description: String? = null
)

data class CatUpdateRequest(
    val name: String? = null,
    val breed: String? = null,
    val color: String? = null,
    val birthDate: LocalDate? = null,
    val weight: Double? = null,
    val gender: CatGender? = null,
    val isNeutered: Boolean? = null,
    val profileImageUrl: String? = null,
    val description: String? = null
)

data class CatResponse(
    val id: Long,
    val name: String,
    val breed: String?,
    val color: String?,
    val birthDate: LocalDate?,
    val weight: Double?,
    val gender: CatGender?,
    val isNeutered: Boolean,
    val profileImageUrl: String?,
    val description: String?,
    val userId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(cat: Cat): CatResponse {
            return CatResponse(
                id = cat.id,
                name = cat.name,
                breed = cat.breed,
                color = cat.color,
                birthDate = cat.birthDate,
                weight = cat.weight,
                gender = cat.gender,
                isNeutered = cat.isNeutered,
                profileImageUrl = cat.profileImageUrl,
                description = cat.description,
                userId = cat.user?.id ?: 0,
                createdAt = cat.createdAt,
                updatedAt = cat.updatedAt
            )
        }
    }
}

data class CatSummaryResponse(
    val id: Long,
    val name: String,
    val breed: String?,
    val profileImageUrl: String?,
    val healthRecordCount: Int,
    val mealRecordCount: Int
) 