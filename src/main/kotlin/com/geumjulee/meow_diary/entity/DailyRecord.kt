package com.geumjulee.meow_diary.entity

import com.geumjulee.meow_diary.dto.DailyRecordDto
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "daily_records")
class DailyRecord : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    var cat: Cat? = null

    @Column(name = "record_date", nullable = false)
    var recordDate: LocalDate? = null

    @Column(name = "title", nullable = false)
    var title: String? = null

    @Column(name = "content", columnDefinition = "TEXT")
    var content: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "mood")
    var mood: Mood? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    var activityType: ActivityType? = null

    @Column(name = "weather")
    var weather: String? = null

    @Column(name = "temperature")
    var temperature: Float? = null

    @Column(name = "special_notes", columnDefinition = "TEXT")
    var specialNotes: String? = null

    @Column(name = "is_important")
    var isImportant: Boolean = false

    @Column(name = "tags")
    var tags: String? = null // 쉼표로 구분된 태그들

    fun toDto(): DailyRecordDto {
        return DailyRecordDto(
            id = this.id,
            userId = this.user?.id,
            catId = this.cat?.id,
            recordDate = this.recordDate,
            title = this.title,
            content = this.content,
            mood = this.mood,
            activityType = this.activityType,
            weather = this.weather,
            temperature = this.temperature,
            specialNotes = this.specialNotes,
            isImportant = this.isImportant,
            tags = this.tags
        )
    }

    enum class Mood {
        VERY_HAPPY, HAPPY, NORMAL, SAD, ANGRY, EXCITED, CALM, PLAYFUL, SLEEPY
    }

    enum class ActivityType {
        PLAYING, SLEEPING, EATING, GROOMING, EXPLORING, CUDDLING, TRAINING, VET_VISIT, OUTDOOR_TIME, RESTING
    }
} 