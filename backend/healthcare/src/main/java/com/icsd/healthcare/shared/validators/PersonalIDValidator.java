package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonalIDValidator implements ConstraintValidator<ValidPersonalID, String> {

    @Override
    public void initialize(ValidPersonalID constraintAnnotation) {
        //It's empty because we don't need a default value on PersonalID
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check if the PersonalID starts with 2 letters and continue by 6 digits
        log.debug("Inside PersonalID Validator isValid");
        return value != null && value.matches("\\p{L}{2}\\d{6}");
    }
}
