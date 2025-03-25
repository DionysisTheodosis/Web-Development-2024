package com.icsd.healthcare.patient;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException(String message) {
        super(message);
    }
    public PatientNotFoundException() {
        super(ErrorCode.ERROR_PATIENT_NOT_FOUND);
    }
}
