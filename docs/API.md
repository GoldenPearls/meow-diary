# MeowDiary API 문서

## 📋 개요

MeowDiary API는 고양이 건강 관리 및 커뮤니티 기능을 제공하는 RESTful API입니다.

### 기본 정보

- **Base URL**: `http://localhost:8080/api`
- **Content-Type**: `application/json`
- **인증**: 현재 개발 중 (향후 JWT 구현 예정)

## 🔐 인증

현재 개발 단계에서는 인증이 비활성화되어 있습니다. 향후 JWT 기반 인증이 구현될 예정입니다.

## 📊 응답 형식

### 성공 응답

```json
{
  "success": true,
  "data": {
    // 응답 데이터
  },
  "message": "성공 메시지"
}
```

### 에러 응답

```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "에러 메시지",
    "details": "상세 정보"
  },
  "timestamp": "2025-07-27T14:50:00.000Z",
  "path": "/api/endpoint"
}
```

## 👥 사용자 관리 API

### 사용자 등록

```http
POST /api/users/register
```

**Request Body:**

```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "nickname": "string"
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "user123",
    "email": "user@example.com",
    "nickname": "고양이집사",
    "role": "USER",
    "isActive": true,
    "createdAt": "2025-07-27T14:50:00.000Z"
  },
  "message": "사용자가 성공적으로 등록되었습니다."
}
```

### 사용자 조회

```http
GET /api/users/{id}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "user123",
    "email": "user@example.com",
    "nickname": "고양이집사",
    "role": "USER",
    "isActive": true,
    "createdAt": "2025-07-27T14:50:00.000Z",
    "updatedAt": "2025-07-27T14:50:00.000Z"
  }
}
```

### 사용자 정보 수정

```http
PUT /api/users/{id}
```

**Request Body:**

```json
{
  "nickname": "새로운닉네임",
  "email": "newemail@example.com"
}
```

### 사용자 비활성화

```http
DELETE /api/users/{id}
```

## 🐱 고양이 관리 API

### 고양이 등록

```http
POST /api/cats
```

**Request Body:**

```json
{
  "name": "루시",
  "breed": "러시안블루",
  "color": "회색",
  "birthDate": "2020-03-15",
  "weight": 4.2,
  "gender": "FEMALE",
  "isNeutered": true,
  "description": "조용하고 예민한 고양이입니다."
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "루시",
    "breed": "러시안블루",
    "color": "회색",
    "birthDate": "2020-03-15",
    "weight": 4.2,
    "gender": "FEMALE",
    "isNeutered": true,
    "description": "조용하고 예민한 고양이입니다.",
    "userId": 1,
    "createdAt": "2025-07-27T14:50:00.000Z"
  }
}
```

### 고양이 목록 조회

```http
GET /api/cats
```

**Query Parameters:**

- `userId` (optional): 사용자 ID로 필터링
- `breed` (optional): 품종으로 필터링
- `gender` (optional): 성별로 필터링

**Response:**

```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "루시",
      "breed": "러시안블루",
      "color": "회색",
      "birthDate": "2020-03-15",
      "weight": 4.2,
      "gender": "FEMALE",
      "isNeutered": true,
      "description": "조용하고 예민한 고양이입니다.",
      "userId": 1,
      "createdAt": "2025-07-27T14:50:00.000Z"
    }
  ]
}
```

### 고양이 상세 조회

```http
GET /api/cats/{id}
```

### 고양이 정보 수정

```http
PUT /api/cats/{id}
```

### 고양이 삭제

```http
DELETE /api/cats/{id}
```

## 💊 건강 기록 API

### 건강 기록 등록

```http
POST /api/health-records
```

**Request Body:**

```json
{
  "catId": 1,
  "recordDate": "2025-07-27",
  "weight": 4.2,
  "temperature": 38.5,
  "heartRate": 120,
  "healthStatus": "NORMAL",
  "symptoms": "기침이 있습니다.",
  "medications": "항생제 처방",
  "vaccinationDate": "2025-07-20",
  "vaccinationType": "3종 혼합백신",
  "veterinaryNotes": "정기 검진 결과 양호"
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "catId": 1,
    "recordDate": "2025-07-27T00:00:00.000Z",
    "weight": 4.2,
    "temperature": 38.5,
    "heartRate": 120,
    "healthStatus": "NORMAL",
    "symptoms": "기침이 있습니다.",
    "medications": "항생제 처방",
    "vaccinationDate": "2025-07-20T00:00:00.000Z",
    "vaccinationType": "3종 혼합백신",
    "veterinaryNotes": "정기 검진 결과 양호",
    "createdAt": "2025-07-27T14:50:00.000Z"
  }
}
```

### 건강 기록 목록 조회

```http
GET /api/health-records
```

**Query Parameters:**

- `catId` (optional): 고양이 ID로 필터링
- `healthStatus` (optional): 건강 상태로 필터링
- `startDate` (optional): 시작 날짜
- `endDate` (optional): 종료 날짜

### 건강 기록 상세 조회

```http
GET /api/health-records/{id}
```

### 건강 기록 수정

```http
PUT /api/health-records/{id}
```

### 건강 기록 삭제

```http
DELETE /api/health-records/{id}
```

## 🍽️ 식단 기록 API

### 식단 기록 등록

```http
POST /api/meal-records
```

**Request Body:**

```json
{
  "catId": 1,
  "mealDate": "2025-07-27T12:00:00.000Z",
  "foodName": "고급 캣푸드",
  "foodType": "DRY_FOOD",
  "amount": 50,
  "unit": "g",
  "calories": 200,
  "ingredients": "닭고기, 쌀, 비타민",
  "feedingTime": "LUNCH",
  "appetiteLevel": "NORMAL",
  "notes": "평소보다 조금 적게 먹었습니다."
}
```

### 식단 기록 목록 조회

```http
GET /api/meal-records
```

**Query Parameters:**

- `catId` (optional): 고양이 ID로 필터링
- `foodType` (optional): 음식 타입으로 필터링
- `feedingTime` (optional): 급여 시간으로 필터링
- `startDate` (optional): 시작 날짜
- `endDate` (optional): 종료 날짜

## 👥 커뮤니티 API

### 게시글 등록

```http
POST /api/community/posts
```

**Request Body:**

```json
{
  "title": "고양이 건강 관리 팁",
  "content": "정기적인 검진이 중요합니다...",
  "category": "HEALTH",
  "userId": 1
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "title": "고양이 건강 관리 팁",
    "content": "정기적인 검진이 중요합니다...",
    "category": "HEALTH",
    "userId": 1,
    "viewCount": 0,
    "likeCount": 0,
    "commentCount": 0,
    "isPinned": false,
    "isDeleted": false,
    "createdAt": "2025-07-27T14:50:00.000Z"
  }
}
```

### 게시글 목록 조회

```http
GET /api/community/posts
```

**Query Parameters:**

- `category` (optional): 카테고리로 필터링
- `userId` (optional): 작성자 ID로 필터링
- `keyword` (optional): 제목/내용 검색
- `page` (optional): 페이지 번호 (기본값: 0)
- `size` (optional): 페이지 크기 (기본값: 10)

**Response:**

```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "title": "고양이 건강 관리 팁",
        "content": "정기적인 검진이 중요합니다...",
        "category": "HEALTH",
        "userId": 1,
        "viewCount": 15,
        "likeCount": 5,
        "commentCount": 3,
        "createdAt": "2025-07-27T14:50:00.000Z"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "currentPage": 0,
    "size": 10
  }
}
```

### 게시글 상세 조회

```http
GET /api/community/posts/{id}
```

### 게시글 수정

```http
PUT /api/community/posts/{id}
```

### 게시글 삭제 (소프트 삭제)

```http
DELETE /api/community/posts/{id}
```

### 게시글 좋아요

```http
POST /api/community/posts/{id}/like
```

**Request Body:**

```json
{
  "userId": 1,
  "likeType": "LIKE"
}
```

### 댓글 등록

```http
POST /api/community/posts/{postId}/comments
```

**Request Body:**

```json
{
  "content": "좋은 정보 감사합니다!",
  "userId": 2,
  "parentCommentId": null
}
```

### 댓글 목록 조회

```http
GET /api/community/posts/{postId}/comments
```

## 🤖 AI 조언 API

### AI 조언 요청

```http
POST /api/ai-queries
```

**Request Body:**

```json
{
  "userId": 1,
  "catId": 1,
  "query": "고양이가 식욕이 없어요. 어떻게 해야 할까요?",
  "queryType": "HEALTH_ADVICE"
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "catId": 1,
    "query": "고양이가 식욕이 없어요. 어떻게 해야 할까요?",
    "queryType": "HEALTH_ADVICE",
    "status": "PENDING",
    "createdAt": "2025-07-27T14:50:00.000Z"
  }
}
```

### AI 조언 처리

```http
PUT /api/ai-queries/{id}/process
```

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "userId": 1,
    "catId": 1,
    "query": "고양이가 식욕이 없어요. 어떻게 해야 할까요?",
    "response": "고양이의 식욕 부진은 여러 원인이 있을 수 있습니다. 스트레스, 질병, 환경 변화 등을 확인해보세요. 24시간 이상 식사를 거부한다면 수의사와 상담을 권장합니다.",
    "queryType": "HEALTH_ADVICE",
    "status": "COMPLETED",
    "confidenceScore": 0.85,
    "modelUsed": "gpt-4",
    "processedAt": "2025-07-27T14:51:00.000Z"
  }
}
```

### AI 조언 목록 조회

```http
GET /api/ai-queries
```

**Query Parameters:**

- `userId` (optional): 사용자 ID로 필터링
- `catId` (optional): 고양이 ID로 필터링
- `status` (optional): 상태로 필터링
- `queryType` (optional): 조언 타입으로 필터링

### AI 조언 상세 조회

```http
GET /api/ai-queries/{id}
```

### AI 조언 삭제

```http
DELETE /api/ai-queries/{id}
```

## 📸 이미지 업로드 API

### 이미지 업로드

```http
POST /api/images/upload
Content-Type: multipart/form-data
```

**Form Data:**

- `file`: 이미지 파일
- `imageType`: `PROFILE`, `HEALTH`, `MEAL`, `COMMUNITY`, `GENERAL`
- `entityId`: 관련 엔티티 ID (고양이, 건강기록 등)
- `description`: 이미지 설명

**Response:**

```json
{
  "success": true,
  "data": {
    "id": 1,
    "fileName": "cat_profile_123.jpg",
    "originalFileName": "my_cat.jpg",
    "filePath": "/uploads/cat_profile_123.jpg",
    "fileSize": 1024000,
    "contentType": "image/jpeg",
    "imageType": "PROFILE",
    "description": "고양이 프로필 사진",
    "entityId": 1,
    "createdAt": "2025-07-27T14:50:00.000Z"
  }
}
```

### 이미지 조회

```http
GET /api/images/{id}
```

### 이미지 삭제

```http
DELETE /api/images/{id}
```

## 📊 통계 API

### 대시보드 통계

```http
GET /api/statistics/dashboard
```

**Response:**

```json
{
  "success": true,
  "data": {
    "totalCats": 1234,
    "totalHealthRecords": 5678,
    "totalUsers": 890,
    "totalAiQueries": 456,
    "recentHealthRecords": [
      {
        "id": 1,
        "catName": "루시",
        "healthStatus": "NORMAL",
        "recordDate": "2025-07-27T14:50:00.000Z"
      }
    ],
    "popularPosts": [
      {
        "id": 1,
        "title": "고양이 건강 관리 팁",
        "viewCount": 150,
        "likeCount": 25
      }
    ]
  }
}
```

## 🔍 검색 API

### 통합 검색

```http
GET /api/search
```

**Query Parameters:**

- `q`: 검색어
- `type`: 검색 타입 (`cats`, `posts`, `health-records`)
- `page`: 페이지 번호
- `size`: 페이지 크기

**Response:**

```json
{
  "success": true,
  "data": {
    "cats": [
      {
        "id": 1,
        "name": "루시",
        "breed": "러시안블루"
      }
    ],
    "posts": [
      {
        "id": 1,
        "title": "고양이 건강 관리 팁",
        "content": "정기적인 검진이 중요합니다..."
      }
    ],
    "healthRecords": [
      {
        "id": 1,
        "catName": "루시",
        "healthStatus": "NORMAL"
      }
    ]
  }
}
```

## 🚨 에러 코드

| 코드                      | 메시지                          | 설명                       |
| ------------------------- | ------------------------------- | -------------------------- |
| `USER_NOT_FOUND`          | 사용자를 찾을 수 없습니다.      | 존재하지 않는 사용자 ID    |
| `CAT_NOT_FOUND`           | 고양이를 찾을 수 없습니다.      | 존재하지 않는 고양이 ID    |
| `HEALTH_RECORD_NOT_FOUND` | 건강 기록을 찾을 수 없습니다.   | 존재하지 않는 건강 기록 ID |
| `POST_NOT_FOUND`          | 게시글을 찾을 수 없습니다.      | 존재하지 않는 게시글 ID    |
| `INVALID_REQUEST`         | 잘못된 요청입니다.              | 요청 데이터 검증 실패      |
| `DUPLICATE_USERNAME`      | 이미 사용 중인 사용자명입니다.  | 중복된 사용자명            |
| `DUPLICATE_EMAIL`         | 이미 사용 중인 이메일입니다.    | 중복된 이메일              |
| `FILE_UPLOAD_ERROR`       | 파일 업로드에 실패했습니다.     | 파일 업로드 오류           |
| `AI_PROCESSING_ERROR`     | AI 처리 중 오류가 발생했습니다. | AI 처리 실패               |

## 📝 예제

### cURL 예제

#### 고양이 등록

```bash
curl -X POST http://localhost:8080/api/cats \
  -H "Content-Type: application/json" \
  -d '{
    "name": "루시",
    "breed": "러시안블루",
    "color": "회색",
    "birthDate": "2020-03-15",
    "weight": 4.2,
    "gender": "FEMALE",
    "isNeutered": true,
    "description": "조용하고 예민한 고양이입니다."
  }'
```

#### 건강 기록 등록

```bash
curl -X POST http://localhost:8080/api/health-records \
  -H "Content-Type: application/json" \
  -d '{
    "catId": 1,
    "recordDate": "2025-07-27",
    "weight": 4.2,
    "temperature": 38.5,
    "healthStatus": "NORMAL",
    "symptoms": "기침이 있습니다."
  }'
```

#### 게시글 등록

```bash
curl -X POST http://localhost:8080/api/community/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "고양이 건강 관리 팁",
    "content": "정기적인 검진이 중요합니다...",
    "category": "HEALTH",
    "userId": 1
  }'
```

## 🔄 상태 코드

| 코드 | 설명                  |
| ---- | --------------------- |
| 200  | 성공                  |
| 201  | 생성됨                |
| 400  | 잘못된 요청           |
| 401  | 인증 필요             |
| 403  | 접근 거부             |
| 404  | 리소스를 찾을 수 없음 |
| 409  | 충돌 (중복 등)        |
| 500  | 서버 내부 오류        |

---

**MeowDiary API** - 고양이 건강 관리의 모든 것 🐱💕
