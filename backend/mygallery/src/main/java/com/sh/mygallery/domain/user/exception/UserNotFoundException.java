package com.sh.mygallery.domain.user.exception;

/**
 * 회원가입하지 않은 유저가 로그인을 시도할 경우 반환할 Exception
 *
 * @author 이세형
 * @since 2025-12-16
 */
public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
