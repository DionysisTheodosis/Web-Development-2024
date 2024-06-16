package com.icsd.healthcare.shared.exception;

public class NoChangesDetectedException extends RuntimeException {
    public NoChangesDetectedException(String message) {
        super(message);
    }

    public NoChangesDetectedException() {
        super("Error: no changes detected");
    }
}
