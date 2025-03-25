package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class UnsupportedFileTypeException extends RuntimeException {

    public UnsupportedFileTypeException(String message) {
        super(message);
    }

    public UnsupportedFileTypeException() {
        super(ErrorCode.ERROR_UNSUPPORTED_FILE_TYPE);
    }
}