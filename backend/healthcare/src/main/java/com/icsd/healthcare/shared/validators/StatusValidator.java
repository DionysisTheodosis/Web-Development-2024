package com.icsd.healthcare.shared.validators;

import com.icsd.healthcare.appointment.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatusValidator implements ConstraintValidator<ValidStatus, Status> {

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
        //It's empty because we don't need a default value on status
    }
    @Override
    public boolean isValid(Status value, ConstraintValidatorContext context) {
        // Check if the Status is either CREATED or ATTENDED OR COMPLETED OR CANCELED
        log.debug("Inside Status Validator isValid");
        return value != null && (value.equals(Status.COMPLETED) || value.equals(Status.ATTENDED) || value.equals(Status.CANCELED) || value.equals(Status.CREATED));
    }
}