package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoctorRoleValidator implements ConstraintValidator<ValidDoctorRole, String> {

    @Override
    public void initialize(ValidDoctorRole constraintAnnotation) {
        //It's empty because we don't need a default value on USER ROLE
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the field or parameter equals DOCTOR
        log.debug("Inside DoctorRoleValidator isValid");
        return value != null && value.equals("DOCTOR");
    }
}