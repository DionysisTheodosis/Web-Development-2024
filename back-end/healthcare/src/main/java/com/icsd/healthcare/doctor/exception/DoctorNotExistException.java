package com.icsd.healthcare.doctor.exception;

public class DoctorNotExistException extends RuntimeException {
    public DoctorNotExistException(String message) {
        super(message);
    }
}
