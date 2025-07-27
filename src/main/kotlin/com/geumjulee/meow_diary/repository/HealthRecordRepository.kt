package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.HealthRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface HealthRecordRepository : JpaRepository<HealthRecord, Long> {
    
    @Query("SELECT hr FROM HealthRecord hr WHERE hr.cat.id = :catId")
    fun findByCatId(@Param("catId") catId: Long): List<HealthRecord>
    
    @Query("SELECT hr FROM HealthRecord hr WHERE hr.cat.id = :catId ORDER BY hr.recordDate DESC")
    fun findByCatIdOrderByRecordDateDesc(@Param("catId") catId: Long): List<HealthRecord>
    
    fun findByHealthStatus(healthStatus: String): List<HealthRecord>
    
    @Query("SELECT hr FROM HealthRecord hr WHERE hr.cat.id = :catId AND hr.recordDate BETWEEN :startDate AND :endDate")
    fun findByCatIdAndDateRange(
        @Param("catId") catId: Long,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<HealthRecord>
    
    @Query("SELECT hr FROM HealthRecord hr WHERE hr.cat.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<HealthRecord>
    
    @Query("SELECT hr FROM HealthRecord hr WHERE hr.cat.id = :catId AND hr.healthStatus = :healthStatus")
    fun findByCatIdAndHealthStatus(
        @Param("catId") catId: Long,
        @Param("healthStatus") healthStatus: String
    ): List<HealthRecord>
} 