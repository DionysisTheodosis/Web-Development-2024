package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class AmkaValidator implements ConstraintValidator<ValidAmka, String> {

    @Override
    public void initialize(ValidAmka constraintAnnotation) {
        //It's empty because we don't need a default value on AMKA
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the AMKA is exactly 11 digits
        log.debug("Inside AmkaValidator isValid");
        return value != null && value.matches("\\d{11}");
    }
}

