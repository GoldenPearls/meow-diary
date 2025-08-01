package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.UserRegistrationRequest
import com.geumjulee.meow_diary.entity.User
import com.geumjulee.meow_diary.entity.UserRole
import com.geumjulee.meow_diary.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userService: UserService

    private lateinit var testUser: User
    private lateinit var registrationRequest: UserRegistrationRequest

    @BeforeEach
    fun setUp() {
        testUser = User().apply {
            username = "testuser"
            email = "test@example.com"
            password = "encodedPassword"
            nickname = "테스트닉네임"
            firstName = "테스트"
            lastName = "사용자"
            role = UserRole.USER
            isActive = true
        }

        registrationRequest = UserRegistrationRequest(
            username = "testuser",
            email = "test@example.com",
            password = "password123",
            nickname = "테스트닉네임",
            firstName = "테스트",
            lastName = "사용자"
        )
    }

    @Test
    fun `registerUser should create new user successfully`() {
        // Given
        `when`(userRepository.existsByUsername("testuser")).thenReturn(false)
        `when`(userRepository.existsByEmail("test@example.com")).thenReturn(false)
        `when`(passwordEncoder.encode("password123")).thenReturn("encodedPassword")
        `when`(userRepository.save(any(User::class.java))).thenReturn(testUser)

        // When
        val result = userService.registerUser(registrationRequest)

        // Then
        assertNotNull(result)
        assertEquals("testuser", result.username)
        assertEquals("test@example.com", result.email)
        assertEquals("테스트", result.firstName)
        assertEquals("사용자", result.lastName)
        assertTrue(result.isActive)

        verify(userRepository).existsByUsername("testuser")
        verify(userRepository).existsByEmail("test@example.com")
        verify(passwordEncoder).encode("password123")
        verify(userRepository).save(any(User::class.java))
    }

    @Test
    fun `registerUser should throw exception when username already exists`() {
        // Given
        `when`(userRepository.existsByUsername("testuser")).thenReturn(true)

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            userService.registerUser(registrationRequest)
        }
        assertEquals("이미 사용 중인 아이디입니다", exception.message)

        verify(userRepository).existsByUsername("testuser")
        verify(userRepository, never()).save(any(User::class.java))
    }

    @Test
    fun `registerUser should throw exception when email already exists`() {
        // Given
        `when`(userRepository.existsByUsername("testuser")).thenReturn(false)
        `when`(userRepository.existsByEmail("test@example.com")).thenReturn(true)

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            userService.registerUser(registrationRequest)
        }
        assertEquals("이미 사용 중인 이메일입니다", exception.message)

        verify(userRepository).existsByUsername("testuser")
        verify(userRepository).existsByEmail("test@example.com")
        verify(userRepository, never()).save(any(User::class.java))
    }

    @Test
    fun `getUserById should return user when exists`() {
        // Given
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(testUser))

        // When
        val result = userService.getUserById(1L)

        // Then
        assertNotNull(result)
        assertEquals("testuser", result.username)
        assertEquals("test@example.com", result.email)

        verify(userRepository).findById(1L)
    }

    @Test
    fun `getUserById should throw exception when user not found`() {
        // Given
        `when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        // When & Then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            userService.getUserById(1L)
        }
        assertEquals("사용자를 찾을 수 없습니다", exception.message)

        verify(userRepository).findById(1L)
    }
} 