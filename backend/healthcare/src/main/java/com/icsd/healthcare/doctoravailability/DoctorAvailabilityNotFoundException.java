package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class DoctorAvailabilityNotFoundException extends RuntimeException {

    public DoctorAvailabilityNotFoundException(String message) {
        super(message);
    }
    public DoctorAvailabilityNotFoundException() {
        super(ErrorCode.ERROR_DOCTOR_AVAILABILITY_NOT_FOUND);
    }
}