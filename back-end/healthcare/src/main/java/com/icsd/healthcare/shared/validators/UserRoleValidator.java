package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRoleValidator implements ConstraintValidator<ValidUserRole, String>  {

    @Override
    public void initialize(ValidUserRole constraintAnnotation) {
        //It's empty because we don't need a default value on user role
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the User Role is either DOCTOR or PATIENT OR SECRETARY
        log.debug("Inside UserRole Validator isValid");
        return value != null && (value.equals("DOCTOR") || value.equals("PATIENT") || value.equals("SECRETARY"));
    }
}