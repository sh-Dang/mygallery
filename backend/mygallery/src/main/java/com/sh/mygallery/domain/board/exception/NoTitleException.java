package com.sh.mygallery.domain.board.exception;

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
