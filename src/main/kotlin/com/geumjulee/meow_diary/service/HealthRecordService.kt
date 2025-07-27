package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.HealthRecordCreateRequest
import com.geumjulee.meow_diary.dto.HealthRecordResponse
import com.geumjulee.meow_diary.dto.HealthRecordUpdateRequest
import com.geumjulee.meow_diary.entity.HealthRecord
import com.geumjulee.meow_diary.repository.CatRepository
import com.geumjulee.meow_diary.repository.HealthRecordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class HealthRecordService(
    private val healthRecordRepository: HealthRecordRepository,
    private val catRepository: CatRepository
) {
    
    fun createHealthRecord(request: HealthRecordCreateRequest): HealthRecordResponse {
        val cat = catRepository.findById(request.catId)
            .orElseThrow { IllegalArgumentException("고양이를 찾을 수 없습니다") }
        
        val healthRecord = HealthRecord().apply {
            recordDate = LocalDateTime.now()
            temperature = request.temperature
            weight = request.weight
            heartRate = request.heartRate
            symptoms = request.symptoms
            medications = request.medications
            veterinaryNotes = request.veterinaryNotes
            vaccinationDate = request.vaccinationDate
            vaccinationType = request.vaccinationType
            healthStatus = request.healthStatus
            this.cat = cat
        }
        
        val savedHealthRecord = healthRecordRepository.save(healthRecord)
        return HealthRecordResponse.from(savedHealthRecord)
    }
    
    @Transactional(readOnly = true)
    fun getHealthRecordById(id: Long): HealthRecordResponse {
        val healthRecord = healthRecordRepository.findById(id)
            .orElseThrow { IllegalArgumentException("건강 기록을 찾을 수 없습니다") }
        return HealthRecordResponse.from(healthRecord)
    }
    
    @Transactional(readOnly = true)
    fun getHealthRecordsByCatId(catId: Long): List<HealthRecordResponse> {
        return healthRecordRepository.findByCatIdOrderByRecordDateDesc(catId)
            .map { HealthRecordResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getHealthRecordsByUserId(userId: Long): List<HealthRecordResponse> {
        return healthRecordRepository.findByUserId(userId)
            .map { HealthRecordResponse.from(it) }
    }
    
    fun updateHealthRecord(id: Long, request: HealthRecordUpdateRequest): HealthRecordResponse {
        val healthRecord = healthRecordRepository.findById(id)
            .orElseThrow { IllegalArgumentException("건강 기록을 찾을 수 없습니다") }
        
        request.temperature?.let { healthRecord.temperature = it }
        request.weight?.let { healthRecord.weight = it }
        request.heartRate?.let { healthRecord.heartRate = it }
        request.symptoms?.let { healthRecord.symptoms = it }
        request.medications?.let { healthRecord.medications = it }
        request.veterinaryNotes?.let { healthRecord.veterinaryNotes = it }
        request.vaccinationDate?.let { healthRecord.vaccinationDate = it }
        request.vaccinationType?.let { healthRecord.vaccinationType = it }
        request.healthStatus?.let { healthRecord.healthStatus = it }
        
        val updatedHealthRecord = healthRecordRepository.save(healthRecord)
        return HealthRecordResponse.from(updatedHealthRecord)
    }
    
    fun deleteHealthRecord(id: Long) {
        val healthRecord = healthRecordRepository.findById(id)
            .orElseThrow { IllegalArgumentException("건강 기록을 찾을 수 없습니다") }
        
        healthRecordRepository.delete(healthRecord)
    }
    
    @Transactional(readOnly = true)
    fun getHealthRecordsByStatus(healthStatus: String): List<HealthRecordResponse> {
        return healthRecordRepository.findByHealthStatus(healthStatus)
            .map { HealthRecordResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getHealthRecordsByDateRange(catId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<HealthRecordResponse> {
        return healthRecordRepository.findByCatIdAndDateRange(catId, startDate, endDate)
            .map { HealthRecordResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getHealthRecordsByCatIdAndStatus(catId: Long, healthStatus: String): List<HealthRecordResponse> {
        return healthRecordRepository.findByCatIdAndHealthStatus(catId, healthStatus)
            .map { HealthRecordResponse.from(it) }
    }
} 