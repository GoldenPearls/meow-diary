-- MeowDiary PostgreSQL 초기화 스크립트
-- 데이터베이스와 사용자는 이미 docker-compose에서 생성됨

-- 한국어 설정을 위한 확장 프로그램 설치
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 인덱스 최적화를 위한 확장 프로그램
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- 데이터베이스 설정
ALTER DATABASE meowdiary SET timezone TO 'Asia/Seoul';

-- 기본 데이터 (선택사항)
-- 초기 관리자 계정이나 기본 설정 데이터를 여기에 추가할 수 있습니다

-- 예시: 고양이 품종 기본 데이터
-- INSERT INTO cat_breeds (name, description) VALUES 
-- ('페르시안', '장모종 고양이'),
-- ('러시안 블루', '단모종 회색 고양이'),
-- ('메인쿤', '대형 장모종 고양이');

COMMENT ON DATABASE meowdiary IS 'MeowDiary - 고양이 건강 관리 시스템';