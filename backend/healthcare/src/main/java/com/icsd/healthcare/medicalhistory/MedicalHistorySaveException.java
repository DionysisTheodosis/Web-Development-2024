package com.icsd.healthcare.medicalhistory;


import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class MedicalHistorySaveException extends RuntimeException {
    public MedicalHistorySaveException(String message) {
        super(message);
    }
    public MedicalHistorySaveException() {
        super(ErrorCode.ERROR_MEDICAL_HISTORY_SAVE);
    }
}