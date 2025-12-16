package com.sh.mygallery.domain.board.exception;

/**
 * 로그인 하지 않고 게시글을 작성하려고 할 때 반환할 Exception
 *
 * @author 이세형
 * @since 2025-12-16
 */
public class NotUserException extends BoardException {
    public NotUserException(String message) {
        super(message);
    }

    public NotUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUserException(Throwable cause) {
        super(cause);
    }
}
