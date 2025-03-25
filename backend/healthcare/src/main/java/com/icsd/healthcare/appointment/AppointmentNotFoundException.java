package com.icsd.healthcare.appointment;

import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;

public class AppointmentNotFoundException extends RuntimeException{

    public AppointmentNotFoundException(String message) {
        super(message);
    }
    public AppointmentNotFoundException() {
        super(ErrorCode.ERROR_APPOINTMENT_NOT_FOUND);
    }
}