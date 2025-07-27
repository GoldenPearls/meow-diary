# MeowDiary 개발 가이드

## 🚀 개발 환경 설정

### 1. 사전 요구사항

#### 필수 소프트웨어

- **Java**: 17 이상
- **Gradle**: 7.0 이상
- **Git**: 최신 버전
- **IDE**: IntelliJ IDEA 또는 VS Code

#### 권장 사항

- **Docker**: 컨테이너화된 개발 환경
- **Postman**: API 테스트
- **H2 Console**: 데이터베이스 관리

### 2. 프로젝트 설정

#### 프로젝트 클론

```bash
git clone https://github.com/your-username/meow-diary.git
cd meow-diary
```

#### IDE 설정

1. **IntelliJ IDEA**

   - File → Open → 프로젝트 폴더 선택
   - Gradle 동기화 자동 실행
   - Kotlin 플러그인 설치 확인

2. **VS Code**
   - Kotlin 확장 설치
   - Spring Boot 확장 설치
   - Java 확장 설치

#### Gradle 동기화

```bash
./gradlew clean build
```

### 3. 데이터베이스 설정

#### H2 데이터베이스 (개발용)

```properties
# application.properties
spring.datasource.url=jdbc:h2:mem:meowdiary
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

#### MySQL 데이터베이스 (운영용)

```properties
# application-prod.properties
spring.datasource.url=jdbc:mysql://localhost:3306/meowdiary
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=meowdiary
spring.datasource.password=password
```

## 🏗️ 프로젝트 구조

### 패키지 구조

```
com.geumjulee.meow_diary/
├── config/          # 설정 클래스
├── controller/      # REST 컨트롤러
├── entity/         # JPA 엔티티
├── repository/     # 데이터 접근 계층
├── service/        # 비즈니스 로직
├── dto/           # 데이터 전송 객체
├── exception/     # 예외 처리
└── util/          # 유틸리티 클래스
```

### 레이어 아키텍처

```
┌─────────────────┐
│   Controller    │ ← REST API 엔드포인트
├─────────────────┤
│    Service      │ ← 비즈니스 로직
├─────────────────┤
│   Repository    │ ← 데이터 접근
├─────────────────┤
│   Entity/DTO    │ ← 데이터 모델
└─────────────────┘
```

## 📝 코딩 컨벤션

### Kotlin 코딩 스타일

#### 네이밍 컨벤션

```kotlin
// 클래스명: PascalCase
class UserService

// 함수명: camelCase
fun getUserById(id: Long): User?

// 변수명: camelCase
val userName: String

// 상수: UPPER_SNAKE_CASE
const val MAX_FILE_SIZE = 10 * 1024 * 1024

// enum: PascalCase
enum class HealthStatus {
    NORMAL, SICK, RECOVERING, CRITICAL
}
```

#### 함수 작성

```kotlin
// 좋은 예
fun createUser(userDto: UserDto): User {
    return userRepository.save(userDto.toEntity())
}

// 나쁜 예
fun createUser(u: UserDto): User {
    return userRepository.save(u.toEntity())
}
```

#### null 안전성

```kotlin
// null 안전한 코드
fun getUserName(userId: Long): String? {
    return userRepository.findById(userId)?.name
}

// Elvis 연산자 사용
fun getUserNameOrDefault(userId: Long): String {
    return userRepository.findById(userId)?.name ?: "Unknown"
}
```

### Spring Boot 컨벤션

#### 컨트롤러 작성

```kotlin
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody @Valid userDto: UserDto): ResponseEntity<ApiResponse<UserDto>> {
        val user = userService.createUser(userDto)
        return ResponseEntity.ok(ApiResponse.success(user.toDto()))
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<ApiResponse<UserDto>> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(ApiResponse.success(user.toDto()))
    }
}
```

#### 서비스 작성

```kotlin
@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(userDto: UserDto): User {
        // 비즈니스 로직 검증
        validateUserDto(userDto)

        // 중복 검사
        if (userRepository.existsByUsername(userDto.username)) {
            throw DuplicateUsernameException("이미 사용 중인 사용자명입니다.")
        }

        return userRepository.save(userDto.toEntity())
    }

    private fun validateUserDto(userDto: UserDto) {
        require(userDto.username.isNotBlank()) { "사용자명은 필수입니다." }
        require(userDto.email.isNotBlank()) { "이메일은 필수입니다." }
    }
}
```

## 🧪 테스트 작성

### 단위 테스트

#### 서비스 테스트

```kotlin
@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `사용자 생성 성공`() {
        // Given
        val userDto = UserDto(
            username = "testuser",
            email = "test@example.com",
            password = "password123"
        )
        val user = userDto.toEntity()

        given(userRepository.save(any(User::class.java))).willReturn(user)
        given(userRepository.existsByUsername("testuser")).willReturn(false)

        // When
        val result = userService.createUser(userDto)

        // Then
        assertThat(result.username).isEqualTo("testuser")
        assertThat(result.email).isEqualTo("test@example.com")
        verify(userRepository).save(any(User::class.java))
    }

    @Test
    fun `중복된 사용자명으로 생성 시 예외 발생`() {
        // Given
        val userDto = UserDto(
            username = "existinguser",
            email = "test@example.com",
            password = "password123"
        )

        given(userRepository.existsByUsername("existinguser")).willReturn(true)

        // When & Then
        assertThatThrownBy { userService.createUser(userDto) }
            .isInstanceOf(DuplicateUsernameException::class.java)
            .hasMessage("이미 사용 중인 사용자명입니다.")
    }
}
```

#### 컨트롤러 테스트

```kotlin
@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `사용자 생성 API 테스트`() {
        // Given
        val userDto = UserDto(
            username = "testuser",
            email = "test@example.com",
            password = "password123"
        )
        val user = userDto.toEntity()

        given(userService.createUser(any(UserDto::class.java))).willReturn(user)

        // When & Then
        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(userDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.username").value("testuser"))
    }
}
```

### 통합 테스트

#### 데이터베이스 통합 테스트

```kotlin
@SpringBootTest
@TestPropertySource(properties = [
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
])
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `사용자 생성 및 조회 통합 테스트`() {
        // Given
        val userDto = UserDto(
            username = "integrationtest",
            email = "integration@example.com",
            password = "password123"
        )

        // When
        val createdUser = userService.createUser(userDto)
        val foundUser = userService.getUserById(createdUser.id!!)

        // Then
        assertThat(foundUser).isNotNull()
        assertThat(foundUser!!.username).isEqualTo("integrationtest")
        assertThat(foundUser.email).isEqualTo("integration@example.com")
    }
}
```

## 🔧 설정 관리

### 프로파일별 설정

#### application.properties (기본)

```properties
# 공통 설정
spring.application.name=meow-diary
server.port=8080

# 로깅
logging.level.com.geumjulee.meow_diary=INFO
logging.level.org.springframework.security=DEBUG
```

#### application-dev.properties (개발)

```properties
# 개발 환경 설정
spring.datasource.url=jdbc:h2:mem:meowdiary
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop

# 보안 설정 (개발용)
spring.security.user.name=admin
spring.security.user.password=admin
```

#### application-prod.properties (운영)

```properties
# 운영 환경 설정
spring.datasource.url=jdbc:mysql://localhost:3306/meowdiary
spring.jpa.hibernate.ddl-auto=validate

# 보안 설정 (운영용)
spring.security.user.name=${ADMIN_USERNAME}
spring.security.user.password=${ADMIN_PASSWORD}
```

### 환경 변수 설정

```bash
# 개발 환경
export SPRING_PROFILES_ACTIVE=dev

# 운영 환경
export SPRING_PROFILES_ACTIVE=prod
export ADMIN_USERNAME=admin
export ADMIN_PASSWORD=secure_password
```

## 🔒 보안 설정

### Spring Security 설정

#### 기본 보안 설정

```kotlin
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/api/users/register").permitAll()
                    .anyRequest().authenticated()
            }
            .headers { headers ->
                headers.frameOptions().disable()
            }

        return http.build()
    }
}
```

#### CORS 설정

```kotlin
@Bean
fun corsConfigurationSource(): CorsConfigurationSource {
    val configuration = CorsConfiguration()
    configuration.allowedOriginPatterns = listOf("*")
    configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
    configuration.allowedHeaders = listOf("*")
    configuration.allowCredentials = true

    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", configuration)
    return source
}
```

## 📊 모니터링 및 로깅

### Actuator 설정

```properties
# Actuator 설정
management.endpoints.web.exposure.include=health,info,metrics,env
management.endpoint.health.show-details=always
management.endpoint.metrics.enabled=true
```

### 로깅 설정

```properties
# 로깅 레벨
logging.level.root=INFO
logging.level.com.geumjulee.meow_diary=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# 로그 패턴
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### 커스텀 로깅

```kotlin
@Component
class ApiLoggingAspect {

    private val logger = LoggerFactory.getLogger(ApiLoggingAspect::class.java)

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    fun logApiCall(joinPoint: ProceedingJoinPoint): Any? {
        val methodName = joinPoint.signature.name
        val startTime = System.currentTimeMillis()

        logger.info("API 호출 시작: $methodName")

        return try {
            val result = joinPoint.proceed()
            val endTime = System.currentTimeMillis()
            logger.info("API 호출 완료: $methodName (${endTime - startTime}ms)")
            result
        } catch (e: Exception) {
            logger.error("API 호출 실패: $methodName", e)
            throw e
        }
    }
}
```

## 🚀 배포 가이드

### 개발 환경 배포

```bash
# 애플리케이션 실행
./gradlew bootRun

# 또는 JAR 파일로 실행
./gradlew build
java -jar build/libs/meow-diary-0.0.1-SNAPSHOT.jar
```

### 운영 환경 배포

#### Docker 배포

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/meow-diary-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Docker 이미지 빌드
docker build -t meow-diary .

# Docker 컨테이너 실행
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod meow-diary
```

#### Docker Compose 배포

```yaml
version: "3.8"
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/meowdiary
    depends_on:
      - db

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: meowdiary
      MYSQL_USER: meowdiary
      MYSQL_PASSWORD: password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

## 🔍 디버깅 가이드

### 로그 분석

```bash
# 애플리케이션 로그 확인
tail -f logs/application.log

# 에러 로그만 확인
grep "ERROR" logs/application.log

# 특정 사용자 관련 로그
grep "user123" logs/application.log
```

### 데이터베이스 디버깅

```sql
-- H2 콘솔에서 실행
-- 사용자 테이블 확인
SELECT * FROM users;

-- 고양이 테이블 확인
SELECT * FROM cats;

-- 건강 기록 확인
SELECT * FROM health_records;
```

### API 디버깅

```bash
# API 응답 확인
curl -X GET http://localhost:8080/api/users/1

# 요청/응답 헤더 확인
curl -v -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@example.com"}'
```

## 📚 유용한 리소스

### 공식 문서

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

### 도구

- [H2 Database Console](http://localhost:8080/h2-console)
- [Spring Boot Actuator](http://localhost:8080/actuator)
- [Postman Collection](docs/postman/MeowDiary.postman_collection.json)

### 커뮤니티

- [Spring Boot GitHub](https://github.com/spring-projects/spring-boot)
- [Kotlin GitHub](https://github.com/JetBrains/kotlin)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/spring-boot)

---

**MeowDiary 개발 가이드** - 고양이와 함께하는 개발 🐱💻
