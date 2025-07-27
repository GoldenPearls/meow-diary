package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.AiQueryCreateRequest
import com.geumjulee.meow_diary.dto.AiQueryResponse
import com.geumjulee.meow_diary.service.AiQueryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ai-queries")
class AiQueryController(
    private val aiQueryService: AiQueryService
) {
    
    @PostMapping
    fun createQuery(
        @RequestParam userId: Long,
        @Valid @RequestBody request: AiQueryCreateRequest
    ): ResponseEntity<AiQueryResponse> {
        val aiQuery = aiQueryService.createQuery(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(aiQuery)
    }
    
    @GetMapping("/{id}")
    fun getQueryById(@PathVariable id: Long): ResponseEntity<AiQueryResponse> {
        val aiQuery = aiQueryService.getQueryById(id)
        return ResponseEntity.ok(aiQuery)
    }
    
    @GetMapping("/user/{userId}")
    fun getQueriesByUserId(@PathVariable userId: Long): ResponseEntity<List<AiQueryResponse>> {
        val aiQueries = aiQueryService.getQueriesByUserId(userId)
        return ResponseEntity.ok(aiQueries)
    }
    
    @GetMapping("/cat/{catId}")
    fun getQueriesByCatId(@PathVariable catId: Long): ResponseEntity<List<AiQueryResponse>> {
        val aiQueries = aiQueryService.getQueriesByCatId(catId)
        return ResponseEntity.ok(aiQueries)
    }
    
    @GetMapping("/status/{status}")
    fun getQueriesByStatus(@PathVariable status: String): ResponseEntity<List<AiQueryResponse>> {
        val aiQueries = aiQueryService.getQueriesByStatus(status)
        return ResponseEntity.ok(aiQueries)
    }
    
    @GetMapping("/type/{queryType}")
    fun getQueriesByType(@PathVariable queryType: String): ResponseEntity<List<AiQueryResponse>> {
        val aiQueries = aiQueryService.getQueriesByType(queryType)
        return ResponseEntity.ok(aiQueries)
    }
    
    @PutMapping("/{id}/process")
    fun processQuery(
        @PathVariable id: Long,
        @RequestParam response: String,
        @RequestParam(required = false) confidenceScore: Double?,
        @RequestParam(required = false) modelUsed: String?
    ): ResponseEntity<AiQueryResponse> {
        val aiQuery = aiQueryService.processQuery(id, response, confidenceScore, modelUsed)
        return ResponseEntity.ok(aiQuery)
    }
    
    @PutMapping("/{id}/fail")
    fun failQuery(
        @PathVariable id: Long,
        @RequestParam(required = false) errorMessage: String?
    ): ResponseEntity<AiQueryResponse> {
        val aiQuery = aiQueryService.failQuery(id, errorMessage)
        return ResponseEntity.ok(aiQuery)
    }
    
    @DeleteMapping("/{id}")
    fun deleteQuery(@PathVariable id: Long): ResponseEntity<Void> {
        aiQueryService.deleteQuery(id)
        return ResponseEntity.noContent().build()
    }
    
    @GetMapping("/pending")
    fun getPendingQueries(): ResponseEntity<List<AiQueryResponse>> {
        val aiQueries = aiQueryService.getPendingQueries()
        return ResponseEntity.ok(aiQueries)
    }
    
    @GetMapping("/user/{userId}/status/{status}")
    fun getQueriesByUserIdAndStatus(
        @PathVariable userId: Long,
        @PathVariable status: String
    ): ResponseEntity<List<AiQueryResponse>> {
        val aiQueries = aiQueryService.getQueriesByUserIdAndStatus(userId, status)
        return ResponseEntity.ok(aiQueries)
    }
    
    @GetMapping("/cat/{catId}/type/{queryType}")
    fun getQueriesByCatIdAndType(
        @PathVariable catId: Long,
        @PathVariable queryType: String
    ): ResponseEntity<List<AiQueryResponse>> {
        val aiQueries = aiQueryService.getQueriesByCatIdAndType(catId, queryType)
        return ResponseEntity.ok(aiQueries)
    }
} 