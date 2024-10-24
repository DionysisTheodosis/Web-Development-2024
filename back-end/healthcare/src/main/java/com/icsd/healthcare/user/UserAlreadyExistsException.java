package com.icsd.healthcare.user;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
    public UserAlreadyExistsException() {
        super(ErrorCode.ERROR_USER_ALREADY_EXISTS);
    }
}
