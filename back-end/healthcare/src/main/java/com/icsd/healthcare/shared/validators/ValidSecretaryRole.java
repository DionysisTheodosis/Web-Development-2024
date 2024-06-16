package com.icsd.healthcare.shared.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SecretaryRoleValidator.class)
@Documented

public @interface ValidSecretaryRole {

    String message() default "Invalid user role should be SECRETARY";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
