# 📧 이메일 인증 설정 가이드

## Gmail SMTP 설정 방법

### 1. Gmail 앱 비밀번호 생성

1. **Google 계정 관리** 페이지 접속: https://myaccount.google.com/
2. **보안** 탭 클릭
3. **2단계 인증** 활성화 (필수)
4. **앱 비밀번호** 생성:
   - "앱 비밀번호" 검색 후 클릭
   - 앱 선택: "메일"
   - 기기 선택: "Windows 컴퓨터" (또는 해당 기기)
   - **생성된 16자리 비밀번호를 복사**

### 2. application.properties 설정

```properties
# 이메일 설정 (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=당신의이메일@gmail.com
spring.mail.password=앱비밀번호16자리
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

### 3. 환경 변수 사용 (권장)

보안을 위해 환경 변수를 사용하세요:

```properties
spring.mail.username=${EMAIL_USERNAME:your-email@gmail.com}
spring.mail.password=${EMAIL_PASSWORD:your-app-password}
```

**Windows 환경 변수 설정:**

```cmd
set EMAIL_USERNAME=your-email@gmail.com
set EMAIL_PASSWORD=your-app-password
```

**실행 시 환경 변수 전달:**

```cmd
java -DEMAIL_USERNAME=your-email@gmail.com -DEMAIL_PASSWORD=your-app-password -jar app.jar
```

### 4. 다른 이메일 서비스 설정

#### 네이버 메일

```properties
spring.mail.host=smtp.naver.com
spring.mail.port=465
spring.mail.username=your-id@naver.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
```

#### Outlook/Hotmail

```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=your-email@outlook.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 5. 테스트 방법

1. 서버 시작
2. 회원가입 페이지에서 이메일 인증 테스트
3. 로그에서 이메일 발송 상태 확인

### 6. 문제 해결

**이메일 발송 실패 시:**

1. **Gmail 계정 설정 확인**

   - 2단계 인증 활성화 여부
   - 앱 비밀번호 정확성

2. **방화벽/보안 프로그램**

   - SMTP 포트(587) 차단 여부 확인
   - 안티바이러스 이메일 보호 기능 확인

3. **Gmail 보안 설정**

   - "보안 수준이 낮은 앱의 액세스" 허용 (더 이상 지원되지 않음)
   - 앱 비밀번호 사용 (권장)

4. **네트워크 문제**
   - 회사/학교 네트워크에서 SMTP 차단 여부
   - VPN 사용 시 이메일 차단 여부

### 7. 이메일 템플릿 커스터마이징

`EmailService.kt`의 `buildEmailContent()` 메서드에서 이메일 내용을 수정할 수 있습니다:

```kotlin
private fun buildEmailContent(code: String): String {
    return """
        <html>
        <body style="font-family: Arial, sans-serif;">
            <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                <h2 style="color: #6C5CE7;">🐾 MeowDiary 이메일 인증</h2>
                <p>안녕하세요! MeowDiary입니다.</p>
                <div style="background: #f8f9fa; padding: 20px; border-radius: 8px; text-align: center;">
                    <h3 style="color: #6C5CE7;">인증번호</h3>
                    <h1 style="font-size: 32px; color: #6C5CE7; letter-spacing: 5px;">$code</h1>
                </div>
                <p>• 인증번호는 10분 후 만료됩니다.</p>
                <p>• 본인이 요청하지 않았다면 이 메일을 무시해주세요.</p>
                <hr>
                <p style="color: #6c757d; font-size: 12px;">
                    이 메일은 발신전용입니다. MeowDiary 🐱
                </p>
            </div>
        </body>
        </html>
    """.trimIndent()
}
```

### 8. 보안 권장사항

1. **프로덕션 환경**에서는 반드시 환경 변수 사용
2. **앱 비밀번호**를 코드에 직접 작성하지 않기
3. **이메일 발송 횟수 제한** 구현 (스팸 방지)
4. **SSL/TLS 암호화** 사용

---

✅ 설정이 완료되면 회원가입 시 실제 이메일로 인증번호가 발송됩니다!
