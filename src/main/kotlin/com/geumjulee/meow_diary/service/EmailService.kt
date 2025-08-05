package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.entity.EmailVerification
import com.geumjulee.meow_diary.repository.EmailVerificationRepository
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.random.Random

@Service
@Transactional
class EmailService(
    private val mailSender: JavaMailSender,
    private val emailVerificationRepository: EmailVerificationRepository
) {
    
    @Async
    fun sendVerificationEmail(email: String): String {
        // 기존 미사용 인증 코드 무효화
        emailVerificationRepository.markAllAsUsedByEmail(email)
        
        // 6자리 인증번호 생성
        val verificationCode = generateVerificationCode()
        
        // 인증 정보 저장
        val emailVerification = EmailVerification(
            email = email,
            verificationCode = verificationCode
        )
        emailVerificationRepository.save(emailVerification)
        
        // 이메일 발송
        try {
            val message = SimpleMailMessage().apply {
                setTo(email)
                subject = "[MeowDiary] 이메일 인증번호"
                text = buildEmailContent(verificationCode)
            }
            mailSender.send(message)
        } catch (e: Exception) {
            throw RuntimeException("이메일 발송에 실패했습니다: ${e.message}")
        }
        
        return verificationCode
    }
    
    fun verifyEmailCode(email: String, code: String): Boolean {
        val verification = emailVerificationRepository
            .findByEmailAndVerificationCodeAndIsUsedFalseAndExpiresAtAfter(
                email, code, LocalDateTime.now()
            ) ?: return false
        
        // 인증 완료 처리
        verification.isUsed = true
        verification.isVerified = true
        emailVerificationRepository.save(verification)
        
        return true
    }
    
    fun isEmailVerified(email: String): Boolean {
        val verifications = emailVerificationRepository
            .findByEmailAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(
                email, LocalDateTime.now()
            )
        return verifications.any { it.isVerified }
    }
    
    private fun generateVerificationCode(): String {
        return (100000..999999).random().toString()
    }
    
    private fun buildEmailContent(code: String): String {
        return """
            안녕하세요! MeowDiary입니다. 🐾
            
            이메일 인증을 위한 인증번호를 안내드립니다.
            
            ┌─────────────────────┐
            │  인증번호: $code    │
            └─────────────────────┘
            
            • 인증번호는 10분 후 만료됩니다.
            • 본인이 요청하지 않았다면 이 메일을 무시해주세요.
            
            고양이와 함께하는 건강한 일상! MeowDiary 🐱
            
            ※ 이 메일은 발신전용입니다.
        """.trimIndent()
    }
    
    // 만료된 인증 토큰 정리 (스케줄러로 호출)
    @Transactional
    fun cleanupExpiredTokens() {
        emailVerificationRepository.deleteExpiredTokens(LocalDateTime.now())
    }
}