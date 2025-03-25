package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecretaryRoleValidator implements ConstraintValidator<ValidSecretaryRole, String> {

    @Override
    public void initialize(ValidSecretaryRole constraintAnnotation) {
        //It's empty because we don't need a default value on USER ROLE
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the field or parameter equals SECRETARY
        log.debug("Inside SecretaryRoleValidator isValid");
        return value != null && value.equals("SECRETARY");
    }
}