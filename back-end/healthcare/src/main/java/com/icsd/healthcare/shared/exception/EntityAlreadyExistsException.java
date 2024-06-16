package com.icsd.healthcare.shared.exception;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException() {
        super(ErrorCode.ERROR_ENTITY_ALREADY_EXISTS);
    }
}