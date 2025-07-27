package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.Cat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CatRepository : JpaRepository<Cat, Long> {
    
    fun findByUserId(userId: Long): List<Cat>
    
    fun findByNameContainingIgnoreCase(name: String): List<Cat>
    
    fun findByBreed(breed: String): List<Cat>
    
    @Query("SELECT c FROM Cat c WHERE c.user.id = :userId AND c.name LIKE %:name%")
    fun findByUserIdAndNameContaining(@Param("userId") userId: Long, @Param("name") name: String): List<Cat>
    
    @Query("SELECT c FROM Cat c WHERE c.gender = :gender")
    fun findByGender(@Param("gender") gender: String): List<Cat>
    
    @Query("SELECT c FROM Cat c WHERE c.isNeutered = :isNeutered")
    fun findByNeuteredStatus(@Param("isNeutered") isNeutered: Boolean): List<Cat>
} 