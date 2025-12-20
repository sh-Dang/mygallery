package com.sh.mygallery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 공통 에러 응답 객체 (Error Response DTO)
 * - 예외 발생 시 클라이언트에게 반환되는 JSON 응답의 구조를 정의
 * - GlobalExceptionHandler에서 생성되어 ResponseEntity의 body로 사용된다.
 * 설계 목적:
 * 1. 모든 에러 응답의 포맷을 통일하기 위함
 * 2. 클라이언트(프론트엔드)가 에러를 예측 가능한 구조로 처리하게 하기 위함
 * 3. HTTP 상태 코드 + 사람이 읽을 수 있는 메시지를 함께 제공하기 위함
 *
 * @author 이세형
 * @since 2025-12-20
 */
@Getter
public class ErrorResponse {

    /**
     * 에러 발생 시각
     * - 서버 기준 시간(LocalDateTime)
     * - 로그 및 장애 추적 시점 파악에 사용
     * - 응답 생성 시점에 자동으로 초기화된다.
     */
    private final LocalDateTime timestamp = LocalDateTime.now();

    /**
     * HTTP 상태 코드 값
     * - 예: 400, 401, 404, 500
     * - HttpStatus enum이 아닌 int 값으로 내려
     *   클라이언트에서 숫자 비교를 쉽게 하기 위함
     */
    private final int status;

    /**
     * HTTP 상태 코드에 대한 표준 설명 문구
     *
     * - 예: "Bad Request", "Unauthorized", "Internal Server Error"
     * - HttpStatus.getReasonPhrase()에서 가져온다.
     */
    private final String error;

    /**
     * 실제 에러에 대한 상세 메시지
     *
     * - 개발자가 직접 정의한 메시지
     * - 비즈니스 예외의 경우 사용자에게 보여줄 수 있는 설명
     * - RuntimeException의 경우 내부 정보를 숨긴 일반적인 메시지
     */
    private final String message;

    /**
     * ErrorResponse 생성자
     *
     * @param status  HTTP 상태 코드(enum)
     * @param message 클라이언트에게 전달할 에러 메시지
     *
     * 동작:
     * - HttpStatus enum으로부터
     *   숫자 코드(status)와 표준 문구(error)를 추출한다.
     */
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();              // HTTP 상태 코드 숫자값
        this.error = status.getReasonPhrase();     // 상태 코드에 대한 기본 설명
        this.message = message;                    // 상세 에러 메시지
    }
}