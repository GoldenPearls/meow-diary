@echo off
echo Flutter MeowDiary 앱 빌드 스크립트
echo ==============================

echo 1. 의존성 설치 중...
flutter pub get

echo.
echo 2. JSON 직렬화 코드 생성 중...
flutter packages pub run build_runner build --delete-conflicting-outputs

echo.
echo 3. 코드 분석 중...
flutter analyze

echo.
echo 4. 테스트 실행 중...
flutter test

echo.
echo 5. APK 빌드 중 (Android Debug)...
flutter build apk --debug

echo.
echo 6. 빌드 완료!
echo APK 파일 위치: build\app\outputs\flutter-apk\app-debug.apk

echo.
echo 추가 빌드 옵션:
echo - Release APK: flutter build apk --release
echo - iOS 빌드: flutter build ios
echo - 웹 빌드: flutter build web

pause