package com.sh.mygallery.domain.board.exception;

/**
 * Board와 관련된 모든 예외(Exception)을 처리해 줄 객체
 *
 * @author 이세형
 * @since 2025-11-30
 */
public class BoardException extends RuntimeException {
    public BoardException() {
        super();
    }

    public BoardException(String message) {
        super(message);
    }

    public BoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardException(Throwable cause) {
        super(cause);
    }
}
