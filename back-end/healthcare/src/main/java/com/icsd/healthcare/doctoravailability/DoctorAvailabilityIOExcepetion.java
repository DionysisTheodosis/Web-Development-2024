package com.icsd.healthcare.doctoravailability;

public class DoctorAvailabilityIOExcepetion extends RuntimeException{

    public DoctorAvailabilityIOExcepetion(String message) {
        super(message);
    }
    public DoctorAvailabilityIOExcepetion() {
        super("Error While Parsing Csv File For Doctor Availability Slots");
    }
}
