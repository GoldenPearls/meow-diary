package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
@Table(name = "ai_queries")
class AiQuery : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    
    @NotBlank
    @Column(name = "query", columnDefinition = "TEXT", nullable = false)
    var query: String = ""
    
    @Column(name = "response", columnDefinition = "TEXT")
    var response: String? = null
    
    @Column(name = "query_type")
    @Enumerated(EnumType.STRING)
    var queryType: QueryType = QueryType.HEALTH_ADVICE
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: QueryStatus = QueryStatus.PENDING
    
    @Column(name = "processed_at")
    var processedAt: LocalDateTime? = null
    
    @Column(name = "confidence_score")
    var confidenceScore: Double? = null
    
    @Column(name = "model_used")
    var modelUsed: String? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    var cat: Cat? = null
}

enum class QueryType {
    HEALTH_ADVICE, DIET_RECOMMENDATION, BEHAVIOR_ANALYSIS, SYMPTOM_CHECK, GENERAL_QUESTION
}

enum class QueryStatus {
    PENDING, PROCESSING, COMPLETED, FAILED
} 