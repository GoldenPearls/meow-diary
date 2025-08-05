package com.geumjulee.meow_diary.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduledService(
    private val emailService: EmailService
) {
    
    // 매일 새벽 3시에 만료된 이메일 인증 토큰 정리
    @Scheduled(cron = "0 0 3 * * ?")
    fun cleanupExpiredEmailTokens() {
        emailService.cleanupExpiredTokens()
    }
}