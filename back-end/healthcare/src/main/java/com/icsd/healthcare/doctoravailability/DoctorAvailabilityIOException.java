package com.icsd.healthcare.doctoravailability;

public class DoctorAvailabilityIOException extends RuntimeException{

    public DoctorAvailabilityIOException(String message) {
        super(message);
    }
    public DoctorAvailabilityIOException() {
        super("Error While Parsing Csv File For Doctor Availability Slots");
    }
}
