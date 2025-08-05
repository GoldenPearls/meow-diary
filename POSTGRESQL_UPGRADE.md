# 🐘 PostgreSQL 완전 전환 가이드

## 현재 상태

✅ **PostgreSQL 호환 모드 H2 실행 중**

- PostgreSQL SQL 문법 사용
- PostgreSQL 다이얼렉트 적용
- 개발 및 테스트에 적합

## 실제 PostgreSQL 서버로 전환하기

### 방법 1: Docker 사용 (권장)

#### 1. Docker Desktop 실행

```cmd
# Docker Desktop 프로그램 실행 후
docker-compose up -d postgres
```

#### 2. 실제 PostgreSQL 설정 활성화

```properties
# application-postgres.properties 수정
spring.datasource.url=jdbc:postgresql://localhost:5432/meowdiary
spring.datasource.username=meowuser
spring.datasource.password=meowpassword
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 방법 2: 직접 설치

#### 1. PostgreSQL 다운로드 및 설치

- https://www.postgresql.org/download/windows/
- 설치 시 사용자명: `postgres`, 비밀번호: `meowpassword`

#### 2. 데이터베이스 생성

```sql
CREATE DATABASE meowdiary;
CREATE USER meowuser WITH PASSWORD 'meowpassword';
GRANT ALL PRIVILEGES ON DATABASE meowdiary TO meowuser;
```

#### 3. 설정 파일 수정

위의 PostgreSQL 설정으로 변경

### 방법 3: 클라우드 PostgreSQL (운영환경)

#### AWS RDS PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://your-rds-endpoint:5432/meowdiary
spring.datasource.username=your-username
spring.datasource.password=your-password
```

#### Heroku PostgreSQL

```properties
spring.datasource.url=${DATABASE_URL}
spring.jpa.hibernate.ddl-auto=update
```

## 현재 설정의 장점

### ✅ PostgreSQL 호환 모드의 장점

1. **즉시 실행 가능** - 별도 서버 설치 불필요
2. **PostgreSQL 문법 지원** - 실제 PostgreSQL과 동일한 SQL
3. **개발/테스트 최적화** - 메모리 기반으로 빠른 실행
4. **이식성** - 언제든 실제 PostgreSQL로 전환 가능

### 🔧 실제 PostgreSQL이 필요한 경우

1. **운영 환경 배포**
2. **대용량 데이터 처리**
3. **PostgreSQL 고급 기능 사용**
4. **멀티 인스턴스 연결**

## 설정 확인 방법

### H2 콘솔 접속

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:meowdiary;MODE=PostgreSQL`
- Username: `meowuser`
- Password: `meowpassword`

### PostgreSQL 문법 테스트

```sql
-- PostgreSQL 스타일 시퀀스
SELECT nextval('users_id_seq');

-- PostgreSQL 스타일 날짜 함수
SELECT now(), current_date;

-- PostgreSQL 스타일 JSON 기능
SELECT '{"name": "고양이"}'::json;
```

## 마이그레이션 전략

### 1단계: 현재 (PostgreSQL 호환 H2)

- 개발 및 기능 구현
- 테스트 및 디버깅

### 2단계: 로컬 PostgreSQL

- Docker 또는 직접 설치
- 운영 환경 시뮬레이션

### 3단계: 클라우드 PostgreSQL

- AWS RDS, Heroku 등
- 실제 운영 배포

---

**🚀 현재 PostgreSQL 호환 모드로 완전히 작동 중입니다!**

필요시 언제든 위의 방법으로 실제 PostgreSQL 서버로 전환할 수 있습니다.
