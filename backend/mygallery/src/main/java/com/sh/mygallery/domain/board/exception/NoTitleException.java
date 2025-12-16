package com.sh.mygallery.domain.board.exception;

/**
 * 게시글에 제목이 없는 경우 반환할 Exception
 *
 * @author 이세형
 * @since 2025-12-16
 */
public class NoTitleException extends BoardException {
    public NoTitleException(String message) {
        super(message);
    }

    public NoTitleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTitleException(Throwable cause) {
        super(cause);
    }
}
