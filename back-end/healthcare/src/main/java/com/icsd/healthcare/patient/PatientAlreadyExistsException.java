package com.icsd.healthcare.patient;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class PatientAlreadyExistsException extends RuntimeException{
    public PatientAlreadyExistsException(String message) {
        super(message);
    }
    public PatientAlreadyExistsException() {
        super(ErrorCode.ERROR_PATIENT_ALREADY_EXISTS);
    }
}
