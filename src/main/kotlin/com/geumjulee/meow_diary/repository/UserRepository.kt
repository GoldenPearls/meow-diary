package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    
    fun findByUsername(username: String): Optional<User>
    
    fun findByEmail(email: String): Optional<User>
    
    fun existsByUsername(username: String): Boolean
    
    fun existsByEmail(email: String): Boolean
    
    @Query("SELECT u FROM User u WHERE u.isActive = true")
    fun findAllActiveUsers(): List<User>
    
    @Query("SELECT u FROM User u WHERE u.role = :role")
    fun findByRole(@Param("role") role: String): List<User>
} 