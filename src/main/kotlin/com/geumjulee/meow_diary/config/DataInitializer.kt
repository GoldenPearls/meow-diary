package com.geumjulee.meow_diary.config

import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {
    
    override fun run(vararg args: String?) {
        // 테스트용 사용자 생성
        if (!userRepository.existsByUsername("testuser")) {
            val testUser = User().apply {
                username = "testuser"
                email = "test@example.com"
                password = passwordEncoder.encode("password123")
                firstName = "테스트"
                lastName = "사용자"
                phone = "010-1234-5678"
                address = "서울시 강남구"
                role = UserRole.USER
                isActive = true
            }
            userRepository.save(testUser)
            println("테스트 사용자가 생성되었습니다: testuser / password123")
        }
    }
} 