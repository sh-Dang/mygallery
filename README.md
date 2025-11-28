# 📝 Spring Boot + JWT 게시판 CRUD 프로젝트

그동안 인증·보안(JWT, Spring Security) 위주의 구현에 집중하면서  
상대적으로 **CRUD와 이미지 업로드 처리 경험이 부족하다고 느껴**  
이를 보완하기 위해 만든 **CRUD 집중 연습용 게시판 프로젝트**입니다.

## 작업기간 : 2025/11/27 ~ 
---

## ✅ 프로젝트 목표

- ✅ **Spring Boot + JPA 기반 CRUD 숙련**
- ✅ **JWT 기반 인증/인가 흐름 직접 구현**
- ✅ **게시글 이미지 업로드 및 조회 처리**
- ✅ **실무와 유사한 API 구조 설계**
- ✅ **DB 설계 → API → 보안까지 전 과정 경험**

---

## 🛠 기술 스택

| 구분 | 기술 |
|------|------|
| Backend | Spring Boot |
| ORM | Spring Data JPA |
| Security | Spring Security |
| Authentication | JWT (Access / Refresh) |
| Database | MySQL |
| Build Tool | Gradle |
| Test Tool | Postman |
| Cache | Redis (Refresh Token 관리) |
| File Upload | Multipart + 서버 로컬 저장 |

---

## 🔐 인증 방식

- **JWT 기반 Stateless 인증**
- 로그인 성공 시
  - Access Token → 클라이언트 저장
  - Refresh Token → Redis 저장
- Access Token 만료 시
  - Refresh Token 검증 후 재발급
- 비밀번호 저장 시
  - **BCrypt 단방향 암호화**

---

## 🧩 주요 기능

### ✅ 회원(Auth)

- 회원가입
- 로그인
- 로그아웃
- Access Token 재발급
- JWT 기반 인증 필터 처리

### ✅ 게시판(Board)

- 게시글 등록
- 게시글 목록 조회 (페이징)
- 게시글 상세 조회
- 게시글 수정
- 게시글 삭제

### ✅ 이미지(Image)

- 게시글 이미지 업로드
- 이미지 서버 저장
- 게시글과 이미지 1:N 매핑
- 이미지 URL 반환

---

## 🗂 ERD 구조 요약

- User (회원)
- Board (게시글)
- BoardImage (게시글 이미지)
- RefreshToken (Redis)
- Social_account (소셜로그인 담당)
---

## 🔁 API 흐름 요약

1. 회원가입 → BCrypt 암호화 저장
2. 로그인 → JWT 발급 + Refresh Token Redis 저장
3. 게시글 작성 시 → JWT 인증 필수
4. 게시글 이미지 업로드 → Multipart 처리
5. 토큰 만료 시 → Refresh Token으로 재발급

---
