package com.sh.mygallery.exception;

import com.sh.mygallery.domain.board.exception.BoardException;
import com.sh.mygallery.domain.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 전역 예외 처리 클래스
 *
 * - 모든 @RestController에서 발생하는 예외를 한 곳에서 처리한다.
 * - 컨트롤러/서비스 계층에서 던진 예외를
 *   HTTP 상태 코드 + 일관된 에러 응답(JSON) 형태로 변환한다.
 *
 * 목적:
 * 1. 컨트롤러마다 try-catch를 작성하지 않기 위함
 * 2. API 응답 포맷을 통일하기 위함
 * 3. 예외 발생 시 서버가 죽지 않도록 안전하게 처리하기 위함
 *
 * @author 이세형
 * @since 2025-12-20
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * UserException 처리 메서드
     * - User 도메인과 관련된 비즈니스 예외를 처리한다.
     * - 예: 로그인 실패, 회원 정보 없음, 권한 없음 등
     * 동작 시점:
     * - 컨트롤러 또는 서비스 계층에서 UserException이 throw 되면
     * - Spring이 이 메서드를 자동으로 호출한다.
     * 응답:
     * - HTTP 상태 코드: 400 (BAD_REQUEST)
     * - 응답 바디: ErrorResponse(JSON)
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {

        // 클라이언트에게 내려줄 에러 응답 객체 생성
        // HttpStatus와 실제 예외 메시지를 함께 담는다
        ErrorResponse response =
                new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

        // ResponseEntity를 사용해 HTTP 상태 코드와 응답 바디를 명확히 지정
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * BoardException 처리 메서드
     *
     * - 게시글(Board) 도메인 관련 비즈니스 예외 처리
     * - 예: 게시글 없음, 작성 권한 없음, 제목 누락 등
     *
     * UserException과 동일한 이유로
     * 클라이언트의 잘못된 요청으로 판단하여 400을 반환한다.
     */
    @ExceptionHandler(BoardException.class)
    public ResponseEntity<ErrorResponse> handleBoardException(BoardException ex) {

        ErrorResponse response =
                new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * RuntimeException 처리 메서드 (최후의 방어선)
     *
     * - 위에서 명시적으로 처리하지 않은 모든 런타임 예외를 처리한다.
     * - NullPointerException, IllegalStateException 등 포함
     *
     * 설계 의도:
     * - 예상하지 못한 서버 오류가 발생하더라도
     *   클라이언트에게 스택트레이스를 그대로 노출하지 않기 위함
     *
     * 주의:
     * - 이 메서드는 가장 마지막에 걸려야 한다.
     * - 구체적인 예외(UserException, BoardException)가 우선 처리된다.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        // 서버 로그용 출력
        // 실무에서는 printStackTrace 대신 log.error(...) 사용 권장
        ex.printStackTrace();

        // 클라이언트에게는 내부 구현을 숨긴 일반적인 메시지만 제공
        ErrorResponse response =
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred. Please try again later."
                );

        // HTTP 500 응답 반환
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}