package com.icsd.healthcare.doctoravailability;

public class DoctorAvailabilityAlreadyExistsException extends RuntimeException {

    public DoctorAvailabilityAlreadyExistsException(String message) {
        super(message);
    }
}
