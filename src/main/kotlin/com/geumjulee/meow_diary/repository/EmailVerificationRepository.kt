package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.EmailVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EmailVerificationRepository : JpaRepository<EmailVerification, Long> {
    
    fun findByEmailAndVerificationCodeAndIsUsedFalseAndExpiresAtAfter(
        email: String,
        verificationCode: String,
        now: LocalDateTime
    ): EmailVerification?
    
    fun findByEmailAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(
        email: String,
        now: LocalDateTime
    ): List<EmailVerification>
    
    @Modifying
    @Query("UPDATE EmailVerification e SET e.isUsed = true WHERE e.email = :email AND e.isUsed = false")
    fun markAllAsUsedByEmail(email: String)
    
    @Modifying
    @Query("DELETE FROM EmailVerification e WHERE e.expiresAt < :now")
    fun deleteExpiredTokens(now: LocalDateTime)
}