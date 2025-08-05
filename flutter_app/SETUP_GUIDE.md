# MeowDiary Flutter 앱 설정 가이드

## 🚀 빠른 시작

### 1. Flutter SDK 설치

#### Windows

1. [Flutter 공식 사이트](https://flutter.dev/docs/get-started/install/windows)에서 Flutter SDK 다운로드
2. 압축 해제 후 `flutter\bin`을 시스템 PATH에 추가
3. 명령 프롬프트에서 `flutter --version` 확인

#### macOS

```bash
# Homebrew 사용
brew install flutter

# 또는 직접 설치
git clone https://github.com/flutter/flutter.git -b stable
export PATH="$PATH:`pwd`/flutter/bin"
```

#### Linux

```bash
# Snap 사용
sudo snap install flutter --classic

# 또는 직접 설치
git clone https://github.com/flutter/flutter.git -b stable
export PATH="$PATH:`pwd`/flutter/bin"
```

### 2. 개발 환경 설정

#### Android Studio 설치

1. [Android Studio](https://developer.android.com/studio) 다운로드 및 설치
2. Flutter 플러그인 설치
3. Android SDK 설정

#### VS Code (선택사항)

1. [VS Code](https://code.visualstudio.com/) 설치
2. Flutter 확장 프로그램 설치

### 3. 환경 확인

```bash
flutter doctor
```

모든 체크마크가 초록색이 되도록 설정을 완료하세요.

## 📱 앱 빌드 및 실행

### 1. 프로젝트 의존성 설치

```bash
cd flutter_app
flutter pub get
```

### 2. JSON 직렬화 코드 생성

```bash
flutter packages pub run build_runner build
```

### 3. 앱 실행

```bash
# 개발 모드로 실행
flutter run

# 특정 기기에서 실행
flutter devices  # 사용 가능한 기기 목록 확인
flutter run -d <device_id>
```

### 4. 앱 빌드

```bash
# Android APK (디버그)
flutter build apk --debug

# Android APK (릴리즈)
flutter build apk --release

# iOS 앱 (macOS에서만)
flutter build ios

# 웹 앱
flutter build web
```

## 🔧 개발 도구

### Hot Reload

앱이 실행 중인 상태에서 코드를 수정하고 `r`키를 누르면 즉시 반영됩니다.

### 디버깅

```bash
# 디버그 모드로 실행
flutter run --debug

# 로그 확인
flutter logs
```

### 코드 분석

```bash
# 코드 분석
flutter analyze

# 코드 포맷팅
flutter format .

# 테스트 실행
flutter test
```

## 📋 백엔드 연동 설정

### API 서버 주소 설정

`lib/services/api_service.dart` 파일에서 백엔드 서버 주소를 확인하세요:

```dart
static const String baseUrl = 'http://10.0.2.2:8080/api'; // Android 에뮬레이터
// iOS 시뮬레이터는 'http://localhost:8080/api' 사용
```

### 실제 기기에서 테스트할 때

실제 스마트폰에서 테스트하려면 백엔드 서버의 실제 IP 주소를 사용하세요:

```dart
static const String baseUrl = 'http://192.168.1.100:8080/api'; // 실제 IP 주소
```

## 🚨 문제 해결

### 일반적인 문제들

1. **Flutter doctor 경고**

   ```bash
   flutter doctor --android-licenses  # Android 라이선스 동의
   ```

2. **의존성 충돌**

   ```bash
   flutter clean
   flutter pub get
   ```

3. **빌드 에러**

   ```bash
   flutter clean
   flutter pub get
   flutter packages pub run build_runner clean
   flutter packages pub run build_runner build
   ```

4. **Android 에뮬레이터 연결 안됨**

   - Android Studio에서 AVD Manager 실행
   - 에뮬레이터 생성 및 실행

5. **iOS 시뮬레이터 (macOS)**
   ```bash
   open -a Simulator
   ```

### 성능 최적화

1. **Release 빌드 사용**

   ```bash
   flutter run --release
   ```

2. **앱 크기 분석**
   ```bash
   flutter build apk --analyze-size
   ```

## 📞 지원

문제가 발생하면 다음 리소스를 확인하세요:

- [Flutter 공식 문서](https://flutter.dev/docs)
- [Flutter GitHub](https://github.com/flutter/flutter)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/flutter)

---

🐾 **Happy Coding with MeowDiary!**
