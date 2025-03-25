package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException (String message) {
        super(message);
    }
    public UnauthorizedAccessException () {
        super(ErrorCode.ERROR_UNAUTHORIZED_ACCESS);
    }
}
