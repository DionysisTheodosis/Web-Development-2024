package com.icsd.healthcare.shared.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserRoleValidator.class)
@Documented
public @interface ValidUserRole {

    String message() default "Invalid UserRole format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}