package com.icsd.healthcare.shared.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmkaValidator.class)
@Documented
public @interface ValidAmka {

    String message() default "Invalid AMKA format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

