package com.icsd.healthcare.doctoravailability.exceptions;

public class DoctorAvailabilityAlreadyExistsException extends RuntimeException {

    public DoctorAvailabilityAlreadyExistsException(String message) {
        super(message);
    }
}
