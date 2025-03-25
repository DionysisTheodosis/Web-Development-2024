package com.icsd.healthcare.shared.exception;

public class InvalidSortByParameterException extends RuntimeException {

    public InvalidSortByParameterException(String message) {
        super(message);
    }

    public InvalidSortByParameterException() {
        super("Error: invalid sort parameter");
    }
}