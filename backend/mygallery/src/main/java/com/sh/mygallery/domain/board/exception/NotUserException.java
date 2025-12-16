package com.sh.mygallery.domain.board.exception;

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
