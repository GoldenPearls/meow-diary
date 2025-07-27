package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.AiQueryCreateRequest
import com.geumjulee.meow_diary.dto.AiQueryResponse
import com.geumjulee.meow_diary.entity.AiQuery
import com.geumjulee.meow_diary.entity.QueryStatus
import com.geumjulee.meow_diary.repository.AiQueryRepository
import com.geumjulee.meow_diary.repository.CatRepository
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AiQueryService(
    private val aiQueryRepository: AiQueryRepository,
    private val userRepository: UserRepository,
    private val catRepository: CatRepository
) {
    
    fun createQuery(userId: Long, request: AiQueryCreateRequest): AiQueryResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        val cat = request.catId?.let { catId ->
            catRepository.findById(catId)
                .orElseThrow { IllegalArgumentException("고양이를 찾을 수 없습니다") }
        }
        
        val aiQuery = AiQuery().apply {
            query = request.query
            queryType = request.queryType
            status = QueryStatus.PENDING
            this.user = user
            this.cat = cat
        }
        
        val savedAiQuery = aiQueryRepository.save(aiQuery)
        return AiQueryResponse.from(savedAiQuery)
    }
    
    @Transactional(readOnly = true)
    fun getQueryById(id: Long): AiQueryResponse {
        val aiQuery = aiQueryRepository.findById(id)
            .orElseThrow { IllegalArgumentException("AI 쿼리를 찾을 수 없습니다") }
        return AiQueryResponse.from(aiQuery)
    }
    
    @Transactional(readOnly = true)
    fun getQueriesByUserId(userId: Long): List<AiQueryResponse> {
        return aiQueryRepository.findByUserId(userId)
            .map { AiQueryResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getQueriesByCatId(catId: Long): List<AiQueryResponse> {
        return aiQueryRepository.findByCatId(catId)
            .map { AiQueryResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getQueriesByStatus(status: String): List<AiQueryResponse> {
        return aiQueryRepository.findByStatus(status)
            .map { AiQueryResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getQueriesByType(queryType: String): List<AiQueryResponse> {
        return aiQueryRepository.findByQueryType(queryType)
            .map { AiQueryResponse.from(it) }
    }
    
    fun processQuery(id: Long, response: String, confidenceScore: Double? = null, modelUsed: String? = null): AiQueryResponse {
        val aiQuery = aiQueryRepository.findById(id)
            .orElseThrow { IllegalArgumentException("AI 쿼리를 찾을 수 없습니다") }
        
        aiQuery.response = response
        aiQuery.status = QueryStatus.COMPLETED
        aiQuery.processedAt = LocalDateTime.now()
        aiQuery.confidenceScore = confidenceScore
        aiQuery.modelUsed = modelUsed
        
        val updatedAiQuery = aiQueryRepository.save(aiQuery)
        return AiQueryResponse.from(updatedAiQuery)
    }
    
    fun failQuery(id: Long, errorMessage: String? = null): AiQueryResponse {
        val aiQuery = aiQueryRepository.findById(id)
            .orElseThrow { IllegalArgumentException("AI 쿼리를 찾을 수 없습니다") }
        
        aiQuery.status = QueryStatus.FAILED
        aiQuery.processedAt = LocalDateTime.now()
        aiQuery.response = errorMessage
        
        val updatedAiQuery = aiQueryRepository.save(aiQuery)
        return AiQueryResponse.from(updatedAiQuery)
    }
    
    fun deleteQuery(id: Long) {
        val aiQuery = aiQueryRepository.findById(id)
            .orElseThrow { IllegalArgumentException("AI 쿼리를 찾을 수 없습니다") }
        
        aiQueryRepository.delete(aiQuery)
    }
    
    @Transactional(readOnly = true)
    fun getPendingQueries(): List<AiQueryResponse> {
        return aiQueryRepository.findByStatusOrderByCreatedAtDesc(QueryStatus.PENDING.name)
            .map { AiQueryResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getQueriesByUserIdAndStatus(userId: Long, status: String): List<AiQueryResponse> {
        return aiQueryRepository.findByUserIdAndStatus(userId, status)
            .map { AiQueryResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getQueriesByCatIdAndType(catId: Long, queryType: String): List<AiQueryResponse> {
        return aiQueryRepository.findByCatIdAndQueryType(catId, queryType)
            .map { AiQueryResponse.from(it) }
    }
} 