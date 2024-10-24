package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class NoEntriesException extends RuntimeException {
    public NoEntriesException(String message) {
        super(message);
    }

    public NoEntriesException() {
        super(ErrorCode.ERROR_NO_ENTRIES_PROVIDED);
    }
}