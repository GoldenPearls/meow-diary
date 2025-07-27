package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.MealRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MealRecordRepository : JpaRepository<MealRecord, Long> {
    
    fun findByCatId(catId: Long): List<MealRecord>
    
    fun findByCatIdOrderByMealDateDesc(catId: Long): List<MealRecord>
    
    fun findByFoodType(foodType: String): List<MealRecord>
    
    fun findByFeedingTime(feedingTime: String): List<MealRecord>
    
    @Query("SELECT mr FROM MealRecord mr WHERE mr.cat.id = :catId AND mr.mealDate BETWEEN :startDate AND :endDate")
    fun findByCatIdAndDateRange(
        @Param("catId") catId: Long,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<MealRecord>
    
    @Query("SELECT mr FROM MealRecord mr WHERE mr.cat.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<MealRecord>
    
    @Query("SELECT mr FROM MealRecord mr WHERE mr.cat.id = :catId AND mr.foodType = :foodType")
    fun findByCatIdAndFoodType(
        @Param("catId") catId: Long,
        @Param("foodType") foodType: String
    ): List<MealRecord>
    
    @Query("SELECT mr FROM MealRecord mr WHERE mr.cat.id = :catId AND mr.appetiteLevel = :appetiteLevel")
    fun findByCatIdAndAppetiteLevel(
        @Param("catId") catId: Long,
        @Param("appetiteLevel") appetiteLevel: String
    ): List<MealRecord>
} 