package com.icsd.healthcare.medicalhistory;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class MedicalHistoryNotFoundException extends RuntimeException{

    public MedicalHistoryNotFoundException(String message) {
        super(message);
    }
    public MedicalHistoryNotFoundException() {
        super(ErrorCode.ERROR_MEDICAL_HISTORY_NOT_FOUND);
    }
}
