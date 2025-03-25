package com.icsd.healthcare.shared.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
@Documented
public @interface ValidStatus {

    String message() default "Invalid Status format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}