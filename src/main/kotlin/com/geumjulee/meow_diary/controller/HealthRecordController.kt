package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.HealthRecordCreateRequest
import com.geumjulee.meow_diary.dto.HealthRecordResponse
import com.geumjulee.meow_diary.dto.HealthRecordUpdateRequest
import com.geumjulee.meow_diary.service.HealthRecordService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/health-records")
class HealthRecordController(
    private val healthRecordService: HealthRecordService
) {
    
    @PostMapping
    fun createHealthRecord(@Valid @RequestBody request: HealthRecordCreateRequest): ResponseEntity<HealthRecordResponse> {
        val healthRecord = healthRecordService.createHealthRecord(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(healthRecord)
    }
    
    @GetMapping("/{id}")
    fun getHealthRecordById(@PathVariable id: Long): ResponseEntity<HealthRecordResponse> {
        val healthRecord = healthRecordService.getHealthRecordById(id)
        return ResponseEntity.ok(healthRecord)
    }
    
    @GetMapping("/cat/{catId}")
    fun getHealthRecordsByCatId(@PathVariable catId: Long): ResponseEntity<List<HealthRecordResponse>> {
        val healthRecords = healthRecordService.getHealthRecordsByCatId(catId)
        return ResponseEntity.ok(healthRecords)
    }
    
    @GetMapping("/user/{userId}")
    fun getHealthRecordsByUserId(@PathVariable userId: Long): ResponseEntity<List<HealthRecordResponse>> {
        val healthRecords = healthRecordService.getHealthRecordsByUserId(userId)
        return ResponseEntity.ok(healthRecords)
    }
    
    @PutMapping("/{id}")
    fun updateHealthRecord(@PathVariable id: Long, @Valid @RequestBody request: HealthRecordUpdateRequest): ResponseEntity<HealthRecordResponse> {
        val healthRecord = healthRecordService.updateHealthRecord(id, request)
        return ResponseEntity.ok(healthRecord)
    }
    
    @DeleteMapping("/{id}")
    fun deleteHealthRecord(@PathVariable id: Long): ResponseEntity<Void> {
        healthRecordService.deleteHealthRecord(id)
        return ResponseEntity.noContent().build()
    }
    
    @GetMapping("/status/{healthStatus}")
    fun getHealthRecordsByStatus(@PathVariable healthStatus: String): ResponseEntity<List<HealthRecordResponse>> {
        val healthRecords = healthRecordService.getHealthRecordsByStatus(healthStatus)
        return ResponseEntity.ok(healthRecords)
    }
    
    @GetMapping("/cat/{catId}/date-range")
    fun getHealthRecordsByDateRange(
        @PathVariable catId: Long,
        @RequestParam startDate: LocalDateTime,
        @RequestParam endDate: LocalDateTime
    ): ResponseEntity<List<HealthRecordResponse>> {
        val healthRecords = healthRecordService.getHealthRecordsByDateRange(catId, startDate, endDate)
        return ResponseEntity.ok(healthRecords)
    }
    
    @GetMapping("/cat/{catId}/status/{healthStatus}")
    fun getHealthRecordsByCatIdAndStatus(
        @PathVariable catId: Long,
        @PathVariable healthStatus: String
    ): ResponseEntity<List<HealthRecordResponse>> {
        val healthRecords = healthRecordService.getHealthRecordsByCatIdAndStatus(catId, healthStatus)
        return ResponseEntity.ok(healthRecords)
    }
} 