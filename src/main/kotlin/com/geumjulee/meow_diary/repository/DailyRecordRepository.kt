package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.DailyRecord
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface DailyRecordRepository : JpaRepository<DailyRecord, Long> {

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.cat.id = :catId")
    fun findByCatId(@Param("catId") catId: Long): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId AND dr.cat.id = :catId")
    fun findByUserIdAndCatId(@Param("userId") userId: Long, @Param("catId") catId: Long): List<DailyRecord>

    fun findByRecordDate(recordDate: LocalDate): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId AND dr.recordDate = :recordDate")
    fun findByUserIdAndRecordDate(@Param("userId") userId: Long, @Param("recordDate") recordDate: LocalDate): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.cat.id = :catId AND dr.recordDate = :recordDate")
    fun findByCatIdAndRecordDate(@Param("catId") catId: Long, @Param("recordDate") recordDate: LocalDate): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId AND dr.cat.id = :catId ORDER BY dr.recordDate DESC")
    fun findByUserIdAndCatIdOrderByDateDesc(@Param("userId") userId: Long, @Param("catId") catId: Long): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId AND dr.cat.id = :catId AND dr.recordDate BETWEEN :startDate AND :endDate ORDER BY dr.recordDate DESC")
    fun findByUserIdAndCatIdAndDateRange(
        @Param("userId") userId: Long,
        @Param("catId") catId: Long,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId AND dr.isImportant = true ORDER BY dr.recordDate DESC")
    fun findImportantRecordsByUserId(@Param("userId") userId: Long): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.cat.id = :catId AND dr.mood = :mood ORDER BY dr.recordDate DESC")
    fun findByCatIdAndMood(@Param("catId") catId: Long, @Param("mood") mood: DailyRecord.Mood): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.cat.id = :catId AND dr.activityType = :activityType ORDER BY dr.recordDate DESC")
    fun findByCatIdAndActivityType(@Param("catId") catId: Long, @Param("activityType") activityType: DailyRecord.ActivityType): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.user.id = :userId AND dr.tags LIKE %:tag% ORDER BY dr.recordDate DESC")
    fun findByUserIdAndTagContaining(@Param("userId") userId: Long, @Param("tag") tag: String): List<DailyRecord>

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.cat.id = :catId ORDER BY dr.recordDate DESC")
    fun findRecentRecordsByCatId(@Param("catId") catId: Long, pageable: Pageable): Page<DailyRecord>

    @Query("SELECT COUNT(dr) FROM DailyRecord dr WHERE dr.cat.id = :catId AND dr.recordDate BETWEEN :startDate AND :endDate")
    fun countRecordsByCatIdAndDateRange(
        @Param("catId") catId: Long,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): Long
} 