package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class InvalidParamsException extends RuntimeException {

    public InvalidParamsException(String message) {
        super(message);
    }

    public InvalidParamsException() {
        super(ErrorCode.ERROR_INVALID_FILE_TYPE);
    }
}
