package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.AiQuery
import com.geumjulee.meow_diary.entity.QueryStatus
import com.geumjulee.meow_diary.entity.QueryType
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class AiQueryCreateRequest(
    @field:NotBlank(message = "질문은 필수입니다")
    val query: String,
    
    val queryType: QueryType = QueryType.GENERAL_QUESTION,
    val catId: Long? = null
)

data class AiQueryResponse(
    val id: Long,
    val query: String,
    val response: String?,
    val queryType: QueryType,
    val status: QueryStatus,
    val processedAt: LocalDateTime?,
    val confidenceScore: Double?,
    val modelUsed: String?,
    val userId: Long,
    val catId: Long?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(aiQuery: AiQuery): AiQueryResponse {
            return AiQueryResponse(
                id = aiQuery.id,
                query = aiQuery.query,
                response = aiQuery.response,
                queryType = aiQuery.queryType,
                status = aiQuery.status,
                processedAt = aiQuery.processedAt,
                confidenceScore = aiQuery.confidenceScore,
                modelUsed = aiQuery.modelUsed,
                userId = aiQuery.user?.id ?: 0,
                catId = aiQuery.cat?.id,
                createdAt = aiQuery.createdAt,
                updatedAt = aiQuery.updatedAt
            )
        }
    }
}

data class AiQuerySummaryResponse(
    val id: Long,
    val query: String,
    val queryType: QueryType,
    val status: QueryStatus,
    val createdAt: LocalDateTime
) 