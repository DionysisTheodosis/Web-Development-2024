package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class DateFormatException extends RuntimeException {

    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException() {
        super(ErrorCode.ERROR_NOT_VALID_DATE_FORMAT);
    }
}