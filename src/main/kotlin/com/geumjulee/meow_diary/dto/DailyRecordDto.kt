package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.DailyRecord
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class DailyRecordDto(
    val id: Long? = null,
    val userId: Long? = null,
    val catId: Long? = null,
    val catName: String? = null,
    
    @field:NotNull(message = "기록 날짜는 필수입니다.")
    val recordDate: LocalDate? = null,
    
    @field:NotBlank(message = "제목은 필수입니다.")
    val title: String? = null,
    
    val content: String? = null,
    val mood: DailyRecord.Mood? = null,
    val activityType: DailyRecord.ActivityType? = null,
    val weather: String? = null,
    val temperature: Float? = null,
    val specialNotes: String? = null,
    val isImportant: Boolean = false,
    val tags: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    fun toEntity(): DailyRecord {
        return DailyRecord().apply {
            this.recordDate = this@DailyRecordDto.recordDate
            this.title = this@DailyRecordDto.title
            this.content = this@DailyRecordDto.content
            this.mood = this@DailyRecordDto.mood
            this.activityType = this@DailyRecordDto.activityType
            this.weather = this@DailyRecordDto.weather
            this.temperature = this@DailyRecordDto.temperature
            this.specialNotes = this@DailyRecordDto.specialNotes
            this.isImportant = this@DailyRecordDto.isImportant
            this.tags = this@DailyRecordDto.tags
        }
    }
}

data class DailyRecordResponseDto(
    val id: Long?,
    val userId: Long?,
    val catId: Long?,
    val catName: String?,
    val recordDate: LocalDate?,
    val title: String?,
    val content: String?,
    val mood: DailyRecord.Mood?,
    val activityType: DailyRecord.ActivityType?,
    val weather: String?,
    val temperature: Float?,
    val specialNotes: String?,
    val isImportant: Boolean,
    val tags: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val imageUrls: List<String> = emptyList()
)

fun DailyRecord.toDto(): DailyRecordResponseDto {
    return DailyRecordResponseDto(
        id = this.id,
        userId = this.user?.id,
        catId = this.cat?.id,
        catName = this.cat?.name,
        recordDate = this.recordDate,
        title = this.title,
        content = this.content,
        mood = this.mood,
        activityType = this.activityType,
        weather = this.weather,
        temperature = this.temperature,
        specialNotes = this.specialNotes,
        isImportant = this.isImportant,
        tags = this.tags,
        createdAt = this.createdAt?.toString(),
        updatedAt = this.updatedAt?.toString()
    )
} 