package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class InvalidOperationException extends RuntimeException{

    public InvalidOperationException(String message) {
        super(message);
    }
    public InvalidOperationException() {
        super(ErrorCode.ERROR_INVALID_OPERATION);
    }
}
