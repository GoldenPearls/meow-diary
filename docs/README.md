# MeowDiary 프로젝트 문서

## 📖 프로젝트 개요

MeowDiary는 고양이의 건강과 생활을 관리하고, 커뮤니티에서 정보를 공유할 수 있는 서비스입니다. 고양이 친화적인 UI와 함께 고양이 집사들이 쉽게 사용할 수 있도록 설계되었습니다.

### 🎯 주요 목표

- 고양이 건강 관리 및 기록
- 커뮤니티를 통한 정보 공유
- AI 기반 건강 조언 (향후 확장 예정)
- 고양이 친화적인 사용자 경험

## 🏗️ 기술 스택

### Backend

- **Framework**: Spring Boot 3.4.3
- **Language**: Kotlin
- **Database**: H2 (개발), MySQL (운영)
- **ORM**: Spring Data JPA, Hibernate
- **Security**: Spring Security
- **Build Tool**: Gradle

### Frontend

- **Template Engine**: Thymeleaf
- **CSS Framework**: Bootstrap 5.3.0
- **Icons**: Font Awesome 6.4.0
- **JavaScript**: Vanilla JS

### 개발 도구

- **IDE**: IntelliJ IDEA / VS Code
- **Version Control**: Git
- **Database Console**: H2 Console
- **Monitoring**: Spring Boot Actuator

## 🗄️ 데이터베이스 구조

### 핵심 엔티티

#### 1. Users (사용자)

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(255),
    profile_image_url VARCHAR(255),
    role ENUM('ADMIN', 'USER') NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

#### 2. Cats (고양이)

```sql
CREATE TABLE cats (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    breed VARCHAR(255),
    color VARCHAR(255),
    birth_date DATE NOT NULL,
    weight FLOAT,
    gender ENUM('MALE', 'FEMALE'),
    is_neutered BOOLEAN,
    description TEXT,
    profile_image_url VARCHAR(255),
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

#### 3. Health Records (건강 기록)

```sql
CREATE TABLE health_records (
    id BIGINT PRIMARY KEY,
    cat_id BIGINT NOT NULL,
    record_date TIMESTAMP NOT NULL,
    weight FLOAT,
    temperature FLOAT,
    heart_rate INTEGER,
    health_status ENUM('NORMAL', 'SICK', 'RECOVERING', 'CRITICAL'),
    symptoms TEXT,
    medications TEXT,
    vaccination_date TIMESTAMP,
    vaccination_type VARCHAR(255),
    veterinary_notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

#### 4. Meal Records (식단 기록)

```sql
CREATE TABLE meal_records (
    id BIGINT PRIMARY KEY,
    cat_id BIGINT NOT NULL,
    meal_date TIMESTAMP NOT NULL,
    food_name VARCHAR(255) NOT NULL,
    food_type ENUM('DRY_FOOD', 'WET_FOOD', 'HOMEMADE', 'RAW_FOOD', 'TREATS'),
    amount FLOAT,
    unit VARCHAR(255),
    calories INTEGER,
    ingredients TEXT,
    feeding_time ENUM('BREAKFAST', 'LUNCH', 'DINNER', 'SNACK', 'REGULAR'),
    appetite_level ENUM('VERY_LOW', 'LOW', 'NORMAL', 'HIGH', 'VERY_HIGH'),
    notes TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

#### 5. Community Posts (커뮤니티 게시글)

```sql
CREATE TABLE community_posts (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    category ENUM('GENERAL', 'HEALTH', 'FOOD', 'BEHAVIOR', 'ADOPTION', 'LOST_FOUND', 'TRAINING', 'VETERINARY'),
    view_count INTEGER,
    like_count INTEGER,
    comment_count INTEGER,
    is_pinned BOOLEAN,
    is_deleted BOOLEAN,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

#### 6. AI Queries (AI 조언)

```sql
CREATE TABLE ai_queries (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    cat_id BIGINT,
    query TEXT NOT NULL,
    response TEXT,
    query_type ENUM('HEALTH_ADVICE', 'DIET_RECOMMENDATION', 'BEHAVIOR_ANALYSIS', 'SYMPTOM_CHECK', 'GENERAL_QUESTION'),
    status ENUM('PENDING', 'PROCESSING', 'COMPLETED', 'FAILED'),
    confidence_score FLOAT,
    model_used VARCHAR(255),
    processed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

## 🚀 설치 및 실행

### 1. 사전 요구사항

- Java 17 이상
- Gradle 7.0 이상
- Git

### 2. 프로젝트 클론

```bash
git clone https://github.com/your-username/meow-diary.git
cd meow-diary
```

### 3. 빌드 및 실행

```bash
# 프로젝트 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 4. 접속

- **메인 페이지**: http://localhost:8080
- **H2 콘솔**: http://localhost:8080/h2-console
- **Actuator**: http://localhost:8080/actuator

## 📁 프로젝트 구조

```
meow-diary/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/geumjulee/meow_diary/
│   │   │       ├── MeowDiaryApplication.kt
│   │   │       ├── config/
│   │   │       │   ├── SecurityConfig.kt
│   │   │       │   └── JpaConfig.kt
│   │   │       ├── controller/
│   │   │       │   ├── WebController.kt
│   │   │       │   ├── UserController.kt
│   │   │       │   ├── CatController.kt
│   │   │       │   ├── HealthRecordController.kt
│   │   │       │   ├── CommunityController.kt
│   │   │       │   └── AiQueryController.kt
│   │   │       ├── entity/
│   │   │       │   ├── BaseEntity.kt
│   │   │       │   ├── User.kt
│   │   │       │   ├── Cat.kt
│   │   │       │   ├── HealthRecord.kt
│   │   │       │   ├── MealRecord.kt
│   │   │       │   ├── CommunityPost.kt
│   │   │       │   ├── CommunityComment.kt
│   │   │       │   ├── CommunityLike.kt
│   │   │       │   ├── Image.kt
│   │   │       │   └── AiQuery.kt
│   │   │       ├── repository/
│   │   │       │   ├── UserRepository.kt
│   │   │       │   ├── CatRepository.kt
│   │   │       │   ├── HealthRecordRepository.kt
│   │   │       │   ├── MealRecordRepository.kt
│   │   │       │   ├── CommunityPostRepository.kt
│   │   │       │   └── AiQueryRepository.kt
│   │   │       ├── service/
│   │   │       │   ├── UserService.kt
│   │   │       │   ├── CatService.kt
│   │   │       │   ├── HealthRecordService.kt
│   │   │       │   ├── CommunityService.kt
│   │   │       │   └── AiQueryService.kt
│   │   │       ├── dto/
│   │   │       │   ├── UserDto.kt
│   │   │       │   ├── CatDto.kt
│   │   │       │   ├── HealthRecordDto.kt
│   │   │       │   ├── CommunityDto.kt
│   │   │       │   └── AiQueryDto.kt
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.kt
│   │   │       └── util/
│   │   │           └── FileUtil.kt
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/
│   │       │   ├── index.html
│   │       │   ├── cats.html
│   │       │   ├── health.html
│   │       │   ├── community.html
│   │       │   └── ai-advice.html
│   │       └── static/
│   │           ├── css/
│   │           │   └── style.css
│   │           └── js/
│   │               └── app.js
│   └── test/
│       └── kotlin/
│           └── com/geumjulee/meow_diary/
│               ├── MeowDiaryApplicationTests.kt
│               └── service/
│                   └── UserServiceTest.kt
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

## 🔧 설정

### application.properties 주요 설정

```properties
# 서버 설정
server.port=8080
# server.servlet.context-path=/api  # 주석 처리됨

# 데이터베이스 설정
spring.datasource.url=jdbc:h2:mem:meowdiary
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA 설정
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 콘솔
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# 보안 설정
spring.security.user.name=admin
spring.security.user.password=admin

# 파일 업로드 설정
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Redis 설정 (향후 사용)
spring.redis.host=localhost
spring.redis.port=6379

# Actuator 설정
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always

# Jackson 설정
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.time-zone=Asia/Seoul
```

## 🎨 UI/UX 특징

### 고양이 친화적 디자인

- **색상 팔레트**: 핑크, 민트, 블루 계열의 따뜻한 색상
- **애니메이션**: 귀 흔들기, 깜빡임, 통통 튀기 등 고양이 애니메이션
- **아이콘**: Font Awesome 고양이 관련 아이콘 사용
- **반응형**: 모바일 친화적 레이아웃

### 주요 페이지

1. **메인 페이지** (`/`)

   - 고양이 애니메이션
   - 기능 소개 카드
   - 통계 카운터

2. **고양이 관리** (`/cats`)

   - 고양이 목록 카드 형태
   - 새 고양이 추가 모달
   - 상세 정보 보기

3. **건강 기록** (`/health`)

   - 건강 상태 기록
   - 체중, 체온 관리
   - 상태별 색상 구분

4. **커뮤니티** (`/community`)

   - 게시글 목록
   - 댓글 시스템
   - 좋아요 기능

5. **AI 조언** (`/ai-advice`)
   - 채팅 형태 상담
   - AI 응답 시뮬레이션

## 🔌 API 문서

### 사용자 관리

```http
POST /api/users/register
GET /api/users/{id}
PUT /api/users/{id}
DELETE /api/users/{id}
```

### 고양이 관리

```http
POST /api/cats
GET /api/cats
GET /api/cats/{id}
PUT /api/cats/{id}
DELETE /api/cats/{id}
```

### 건강 기록

```http
POST /api/health-records
GET /api/health-records
GET /api/health-records/{id}
PUT /api/health-records/{id}
DELETE /api/health-records/{id}
```

### 커뮤니티

```http
POST /api/community/posts
GET /api/community/posts
GET /api/community/posts/{id}
PUT /api/community/posts/{id}
DELETE /api/community/posts/{id}
POST /api/community/posts/{id}/like
```

### AI 조언

```http
POST /api/ai-queries
GET /api/ai-queries
GET /api/ai-queries/{id}
PUT /api/ai-queries/{id}/process
DELETE /api/ai-queries/{id}
```

## 🧪 테스트

### 단위 테스트 실행

```bash
./gradlew test
```

### 통합 테스트 실행

```bash
./gradlew integrationTest
```

### 테스트 커버리지 확인

```bash
./gradlew jacocoTestReport
```

## 🔒 보안

### Spring Security 설정

- CSRF 비활성화 (개발 환경)
- CORS 설정
- 정적 리소스 접근 허용
- 웹 페이지 경로 접근 허용

### 주요 보안 경로

```kotlin
.requestMatchers("/").permitAll()
.requestMatchers("/cats").permitAll()
.requestMatchers("/health").permitAll()
.requestMatchers("/community").permitAll()
.requestMatchers("/ai-advice").permitAll()
.requestMatchers("/css/**").permitAll()
.requestMatchers("/js/**").permitAll()
.requestMatchers("/images/**").permitAll()
```

## 📊 모니터링

### Actuator 엔드포인트

- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`

### 로깅

```properties
logging.level.com.geumjulee.meow_diary=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 🚀 배포

### 개발 환경

```bash
./gradlew bootRun
```

### 운영 환경

```bash
./gradlew build
java -jar build/libs/meow-diary-0.0.1-SNAPSHOT.jar
```

### Docker 배포 (향후)

```dockerfile
FROM openjdk:17-jdk-slim
COPY build/libs/meow-diary-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🔮 향후 계획

### 단기 계획 (1-3개월)

- [ ] JWT 기반 인증 시스템 구현
- [ ] 파일 업로드 기능 완성
- [ ] API 문서화 (Swagger/OpenAPI)
- [ ] 단위 테스트 및 통합 테스트 작성

### 중기 계획 (3-6개월)

- [ ] AI 모델 연동 (고양이 건강 조언)
- [ ] 실시간 알림 시스템
- [ ] 모바일 앱 개발
- [ ] 고급 검색 및 필터링 기능

### 장기 계획 (6개월 이상)

- [ ] 변의 상태 분석 AI
- [ ] 고양이 행동 패턴 분석
- [ ] 수의사 연동 시스템
- [ ] 국제화 지원

## 🤝 기여 가이드

### 개발 환경 설정

1. 프로젝트 클론
2. IDE에서 프로젝트 열기
3. Gradle 동기화
4. 애플리케이션 실행

### 코드 컨벤션

- Kotlin 코딩 스타일 가이드 준수
- 의미있는 변수명과 함수명 사용
- 주석 작성
- 테스트 코드 작성

### 이슈 리포트

- GitHub Issues 사용
- 명확한 제목과 설명
- 재현 가능한 단계 포함

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 👥 팀

- **개발자**: [Your Name]
- **디자이너**: [Designer Name]
- **기획자**: [Planner Name]

## 📞 연락처

- **이메일**: your.email@example.com
- **GitHub**: https://github.com/your-username
- **프로젝트**: https://github.com/your-username/meow-diary

---

**MeowDiary** - 고양이와 함께하는 건강한 일상 🐱💕
