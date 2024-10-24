package com.icsd.healthcare.shared.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {

    String message() default "Invalid password format: Should be at least 8 characters long,to contain 1 Capital letter, 1 small letter," +
            "1 special character and 1 digit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}