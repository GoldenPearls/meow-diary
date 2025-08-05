package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "email_verifications")
data class EmailVerification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    val email: String,
    
    @Column(nullable = false, length = 6)
    val verificationCode: String,
    

    
    @Column(nullable = false)
    val expiresAt: LocalDateTime = LocalDateTime.now().plusMinutes(10),
    
    @Column(nullable = false)
    var isUsed: Boolean = false,
    
    @Column(nullable = false)
    var isVerified: Boolean = false
) : BaseEntity()