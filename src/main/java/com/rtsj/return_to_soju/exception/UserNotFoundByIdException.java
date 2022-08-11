package com.rtsj.return_to_soju.exception;

public class UserNotFoundByIdException extends RuntimeException {
    public UserNotFoundByIdException() {
        super("해당 userId로 user를 찾을 수 없습니다.");
    }

    public UserNotFoundByIdException(String message) {
        super(message);
    }

    public UserNotFoundByIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
