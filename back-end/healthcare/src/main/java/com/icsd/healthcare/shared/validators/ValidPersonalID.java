package com.icsd.healthcare.shared.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersonalIDValidator.class)
@Documented
public @interface ValidPersonalID {

    String message() default "Invalid PersonalID format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}