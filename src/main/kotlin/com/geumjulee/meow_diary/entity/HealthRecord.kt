package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "health_records")
class HealthRecord : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    
    @NotNull
    @Column(name = "record_date", nullable = false)
    var recordDate: LocalDateTime = LocalDateTime.now()
    
    @Column(name = "temperature")
    var temperature: Double? = null
    
    @Column(name = "weight")
    var weight: Double? = null
    
    @Column(name = "heart_rate")
    var heartRate: Int? = null
    
    @Column(name = "symptoms", columnDefinition = "TEXT")
    var symptoms: String? = null
    
    @Column(name = "medications", columnDefinition = "TEXT")
    var medications: String? = null
    
    @Column(name = "veterinary_notes", columnDefinition = "TEXT")
    var veterinaryNotes: String? = null
    
    @Column(name = "vaccination_date")
    var vaccinationDate: LocalDateTime? = null
    
    @Column(name = "vaccination_type")
    var vaccinationType: String? = null
    
    @Column(name = "health_status")
    @Enumerated(EnumType.STRING)
    var healthStatus: HealthStatus = HealthStatus.NORMAL
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    var cat: Cat? = null
    
    @OneToMany(mappedBy = "healthRecord", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var images: MutableList<Image> = mutableListOf()
}

enum class HealthStatus {
    NORMAL, SICK, RECOVERING, CRITICAL
} 