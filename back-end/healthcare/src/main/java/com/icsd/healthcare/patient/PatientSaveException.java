package com.icsd.healthcare.patient;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class PatientSaveException extends RuntimeException{
    public PatientSaveException(String message) {
        super(message);
    }
    public PatientSaveException() {
        super(ErrorCode.ERROR_PATIENT_ALREADY_EXISTS);
    }
}