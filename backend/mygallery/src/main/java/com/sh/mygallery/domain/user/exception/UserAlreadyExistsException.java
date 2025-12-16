package com.sh.mygallery.domain.user.exception;

/**
 * 회원가입 시 이미 존재하는 유저일 경우 반환할 Exception
 *
 * @author 이세형
 * @since 2025-12-16
 */
public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
