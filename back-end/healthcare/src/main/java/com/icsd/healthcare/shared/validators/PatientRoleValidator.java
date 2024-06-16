package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PatientRoleValidator implements ConstraintValidator<ValidPatientRole, String> {

    @Override
    public void initialize(ValidPatientRole constraintAnnotation) {
        //It's empty because we don't need a default value on USER ROLE
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the field or parameter equals PATIENT
        log.debug("Inside PatientRoleValidator isValid");
        return value != null && value.equals("PATIENT");
    }
}