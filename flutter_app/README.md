# MeowDiary Flutter App

고양이 건강 관리를 위한 Flutter 모바일 애플리케이션입니다.

## 주요 기능

### 🐱 고양이 관리

- 고양이 프로필 등록/수정/삭제
- 고양이 사진 업로드
- 품종, 색상, 생년월일, 체중 등 상세 정보 관리

### 💝 건강 기록

- 체중, 체온 기록
- 증상 및 진단 내용 기록
- 수의사 방문 기록
- 건강 상태 추적

### 👥 커뮤니티 (예정)

- 다른 고양이 집사들과 정보 공유
- 게시글 작성 및 댓글
- 좋아요 기능

### 🤖 AI 조언 (예정)

- 고양이 건강 상태 분석
- 맞춤형 건강 관리 조언

## 기술 스택

- **Frontend**: Flutter (Dart)
- **State Management**: Provider
- **HTTP Client**: Dio
- **Image Handling**:
  - image_picker (이미지 선택)
  - cached_network_image (이미지 캐싱)
- **Local Storage**: shared_preferences
- **Backend**: Spring Boot (Kotlin) REST API

## 폴더 구조

```
lib/
├── main.dart                 # 앱 진입점
├── models/                   # 데이터 모델
│   ├── user.dart
│   ├── cat.dart
│   └── health_record.dart
├── providers/                # 상태 관리
│   ├── auth_provider.dart
│   └── cat_provider.dart
├── services/                 # API 서비스
│   ├── api_service.dart
│   ├── auth_service.dart
│   └── cat_service.dart
├── screens/                  # 화면들
│   ├── splash_screen.dart
│   ├── home_screen.dart
│   ├── auth/
│   │   ├── login_screen.dart
│   │   └── register_screen.dart
│   ├── cats/
│   │   ├── cats_screen.dart
│   │   ├── add_cat_screen.dart
│   │   └── cat_detail_screen.dart
│   ├── health/
│   ├── community/
│   └── profile/
├── widgets/                  # 재사용 위젯
│   └── cat_card.dart
└── utils/                    # 유틸리티
    └── theme.dart
```

## 설치 및 실행

### 전제 조건

- Flutter SDK 설치
- Android Studio 또는 VS Code
- Android/iOS 개발 환경 설정

### 백엔드 서버 실행

1. Spring Boot 서버를 먼저 실행하세요 (포트: 8080)
2. H2 데이터베이스가 자동으로 설정됩니다

### Flutter 앱 실행

1. 의존성 설치:

```bash
flutter pub get
```

2. 코드 생성 (JSON 직렬화):

```bash
flutter packages pub run build_runner build
```

3. 앱 실행:

```bash
# Android
flutter run

# iOS
flutter run -d ios
```

## API 연동

앱은 Spring Boot 백엔드와 REST API로 통신합니다:

- **Base URL**:
  - Android: `http://10.0.2.2:8080/api`
  - iOS: `http://localhost:8080/api`

### 주요 엔드포인트

- `POST /auth/login` - 로그인
- `POST /users/register` - 회원가입
- `GET /cats/user/{userId}` - 사용자의 고양이 목록
- `POST /cats` - 고양이 등록
- `POST /images/upload` - 이미지 업로드

## 주요 특징

### 🎨 디자인

- Material Design 3 기반
- 고양이 테마 색상 팔레트
- 직관적이고 사용하기 쉬운 UI

### 📱 반응형

- 다양한 화면 크기 지원
- 태블릿 친화적 레이아웃

### 🔐 보안

- JWT 토큰 기반 인증
- 자동 토큰 갱신
- 보안 저장소 사용

### 📸 이미지

- 갤러리에서 사진 선택
- 자동 이미지 압축
- 캐시된 네트워크 이미지

## 개발 상태

- ✅ 인증 시스템 (로그인/회원가입)
- ✅ 고양이 관리 (등록/조회/상세보기)
- ✅ 이미지 업로드
- ✅ 기본 UI/UX
- 🚧 건강 기록 관리 (개발 중)
- 🚧 커뮤니티 기능 (개발 중)
- 🚧 AI 조언 기능 (계획 중)

## 기여하기

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 라이선스

이 프로젝트는 MIT 라이선스 하에 있습니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 문의

문제가 있거나 기능 제안이 있으시면 GitHub Issues를 이용해 주세요.

---

🐾 **MeowDiary** - 고양이와 함께하는 건강한 일상
