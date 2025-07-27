package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.HealthRecord
import com.geumjulee.meow_diary.entity.HealthStatus
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class HealthRecordCreateRequest(
    @field:NotNull(message = "고양이 ID는 필수입니다")
    val catId: Long,
    
    val temperature: Double? = null,
    val weight: Double? = null,
    val heartRate: Int? = null,
    val symptoms: String? = null,
    val medications: String? = null,
    val veterinaryNotes: String? = null,
    val vaccinationDate: LocalDateTime? = null,
    val vaccinationType: String? = null,
    val healthStatus: HealthStatus = HealthStatus.NORMAL
)

data class HealthRecordUpdateRequest(
    val temperature: Double? = null,
    val weight: Double? = null,
    val heartRate: Int? = null,
    val symptoms: String? = null,
    val medications: String? = null,
    val veterinaryNotes: String? = null,
    val vaccinationDate: LocalDateTime? = null,
    val vaccinationType: String? = null,
    val healthStatus: HealthStatus? = null
)

data class HealthRecordResponse(
    val id: Long,
    val recordDate: LocalDateTime,
    val temperature: Double?,
    val weight: Double?,
    val heartRate: Int?,
    val symptoms: String?,
    val medications: String?,
    val veterinaryNotes: String?,
    val vaccinationDate: LocalDateTime?,
    val vaccinationType: String?,
    val healthStatus: HealthStatus,
    val catId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(healthRecord: HealthRecord): HealthRecordResponse {
            return HealthRecordResponse(
                id = healthRecord.id,
                recordDate = healthRecord.recordDate,
                temperature = healthRecord.temperature,
                weight = healthRecord.weight,
                heartRate = healthRecord.heartRate,
                symptoms = healthRecord.symptoms,
                medications = healthRecord.medications,
                veterinaryNotes = healthRecord.veterinaryNotes,
                vaccinationDate = healthRecord.vaccinationDate,
                vaccinationType = healthRecord.vaccinationType,
                healthStatus = healthRecord.healthStatus,
                catId = healthRecord.cat?.id ?: 0,
                createdAt = healthRecord.createdAt,
                updatedAt = healthRecord.updatedAt
            )
        }
    }
}

data class HealthRecordSummaryResponse(
    val id: Long,
    val recordDate: LocalDateTime,
    val healthStatus: HealthStatus,
    val weight: Double?,
    val symptoms: String?
) 