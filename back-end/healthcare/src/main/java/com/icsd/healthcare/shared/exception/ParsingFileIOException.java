package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class ParsingFileIOException extends RuntimeException {
    public ParsingFileIOException(String message) {
        super(message);
    }

    public ParsingFileIOException() {
        super(ErrorCode.ERROR_PARSING_FILE);
    }
}
