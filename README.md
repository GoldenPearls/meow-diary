# 🐱 MeowDiary - 고양이 건강 관리 플랫폼

MeowDiary는 고양이의 건강과 생활을 관리하고, 커뮤니티에서 정보를 공유할 수 있는 서비스입니다. 고양이의 건강 기록, 식단 관리, 커뮤니티 기능을 제공하며, 향후 AI를 통한 건강 조언 서비스로 확장할 예정입니다.

## 📋 프로젝트 개요

### 주요 기능

- **고양이 프로필 관리**: 반려묘 정보 등록 및 관리
- **건강 기록**: 체중, 체온, 증상 등 건강 정보 기록
- **식단 관리**: 먹이 종류, 양, 시간 등 식단 정보 관리
- **커뮤니티**: 고양이 관련 정보 공유 및 소통
- **AI 조언**: 고양이 건강 상태에 대한 AI 분석 및 조언 (향후 구현 예정)

### 기술 스택

- **Backend**: Spring Boot 3.4.3, Kotlin
- **Database**: H2 (개발), MySQL (운영)
- **Security**: Spring Security
- **Build Tool**: Gradle
- **Java Version**: 17

## 🗄️ 데이터베이스 구조

### 주요 엔티티

- **users**: 사용자 정보
- **cats**: 고양이 프로필 정보
- **health_records**: 건강 기록
- **meal_records**: 식단 기록
- **images**: 이미지 파일 정보
- **community_posts**: 커뮤니티 게시글
- **community_comments**: 커뮤니티 댓글
- **community_likes**: 커뮤니티 좋아요
- **ai_queries**: AI 쿼리 및 응답

## 🚀 시작하기

### 필수 요구사항

- Java 17 이상
- Gradle 7.0 이상

### 설치 및 실행

1. **프로젝트 클론**

```bash
git clone https://github.com/your-username/meow-diary.git
cd meow-diary
```

2. **애플리케이션 실행**

```bash
./gradlew bootRun
```

3. **접속**

- 애플리케이션: http://localhost:8080
- H2 콘솔: http://localhost:8080/h2-console
- Actuator: http://localhost:8080/actuator

## 📚 API 문서

### 사용자 관리

```
POST /api/users/register - 사용자 등록
GET /api/users/{id} - 사용자 정보 조회
PUT /api/users/{id} - 사용자 정보 수정
DELETE /api/users/{id} - 사용자 비활성화
```

### 고양이 관리

```
POST /api/cats?userId={userId} - 고양이 등록
GET /api/cats/{id} - 고양이 정보 조회
GET /api/cats/user/{userId} - 사용자의 고양이 목록
PUT /api/cats/{id} - 고양이 정보 수정
DELETE /api/cats/{id} - 고양이 삭제
```

### 건강 기록

```
POST /api/health-records - 건강 기록 등록
GET /api/health-records/{id} - 건강 기록 조회
GET /api/health-records/cat/{catId} - 고양이별 건강 기록
PUT /api/health-records/{id} - 건강 기록 수정
DELETE /api/health-records/{id} - 건강 기록 삭제
```

### 커뮤니티

```
POST /api/community/posts?userId={userId} - 게시글 작성
GET /api/community/posts/{id} - 게시글 조회
GET /api/community/posts - 게시글 목록
PUT /api/community/posts/{id} - 게시글 수정
DELETE /api/community/posts/{id} - 게시글 삭제
POST /api/community/posts/{postId}/like - 좋아요
DELETE /api/community/posts/{postId}/like - 좋아요 취소
```

### AI 쿼리

```
POST /api/ai-queries?userId={userId} - AI 질문 등록
GET /api/ai-queries/{id} - AI 쿼리 조회
PUT /api/ai-queries/{id}/process - AI 응답 처리
GET /api/ai-queries/pending - 대기 중인 쿼리 목록
```

## 🏗️ 프로젝트 구조

```
src/main/kotlin/com/geumjulee/meow_diary/
├── config/                 # 설정 클래스
├── controller/             # REST API 컨트롤러
├── dto/                   # 데이터 전송 객체
├── entity/                # JPA 엔티티
├── exception/             # 예외 처리
├── repository/            # 데이터 접근 계층
├── service/               # 비즈니스 로직
└── util/                  # 유틸리티 클래스
```

## 🔧 개발 환경 설정

### 데이터베이스 설정

현재 H2 인메모리 데이터베이스를 사용하고 있습니다. MySQL로 변경하려면 `application.properties`를 수정하세요.

### 보안 설정

기본적으로 모든 API 엔드포인트가 허용되어 있습니다. 운영 환경에서는 적절한 인증/인가 설정을 추가하세요.

## 🧪 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests MeowDiaryApplicationTests
```

## 📈 향후 계획

### 단기 계획

- [ ] JWT 기반 인증 시스템 구현
- [ ] 파일 업로드 기능 완성
- [ ] API 문서화 (Swagger/OpenAPI)
- [ ] 단위 테스트 및 통합 테스트 작성

### 중기 계획

- [ ] AI 모델 연동 (고양이 건강 조언)
- [ ] 실시간 알림 시스템
- [ ] 모바일 앱 개발
- [ ] 고급 검색 및 필터링 기능

### 장기 계획

- [ ] 변의 상태 분석 AI
- [ ] 고양이 행동 패턴 분석
- [ ] 수의사 연동 시스템
- [ ] 국제화 지원

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 📞 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해 주세요.

---

**MeowDiary** - 고양이와 함께하는 건강한 일상 🐾
