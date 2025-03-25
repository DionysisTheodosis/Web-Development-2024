package com.icsd.healthcare.medicalhistoryrecord;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class MedicalHistoryRecordNotFoundException extends RuntimeException{

    public MedicalHistoryRecordNotFoundException(String message) {
        super(message);
    }
    public MedicalHistoryRecordNotFoundException() {
        super(ErrorCode.ERROR_PATIENT_NOT_FOUND);
    }
}
