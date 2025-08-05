# PostgreSQL 직접 설치 가이드

## Windows에서 PostgreSQL 설치

### 1. PostgreSQL 다운로드

1. https://www.postgresql.org/download/windows/ 접속
2. "Download the installer" 클릭
3. PostgreSQL 15 버전 다운로드

### 2. 설치 과정

1. 다운로드한 파일 실행
2. 설치 경로: 기본값 사용 (C:\Program Files\PostgreSQL\15)
3. 구성요소: 모두 선택 (PostgreSQL Server, pgAdmin 4, Stack Builder, Command Line Tools)
4. 데이터 디렉토리: 기본값 사용
5. **비밀번호 설정**: `meow123!@#` (또는 원하는 비밀번호)
6. 포트: 5432 (기본값)
7. 로케일: Korean, Korea

### 3. 데이터베이스 생성

설치 완료 후 SQL Shell (psql) 실행:

```sql
-- postgres 사용자로 로그인 후
CREATE DATABASE meowdiary;
CREATE USER meowdiary_user WITH PASSWORD 'meow123!@#';
GRANT ALL PRIVILEGES ON DATABASE meowdiary TO meowdiary_user;
```

### 4. pgAdmin으로 확인

1. pgAdmin 4 실행
2. 서버 추가: localhost:5432
3. 데이터베이스 meowdiary 확인

## 연결 설정

application-postgres.properties 파일의 비밀번호를 설치 시 설정한 비밀번호로 변경:

```properties
spring.datasource.password=설치시_설정한_비밀번호
```
