package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Slf4j
@Component
public class GenericValidator {
    private final Validator validator;

    public <T> Set<T> validateAndFilter(Set<T> objects) {
        return objects.stream()
                .filter(this::isValid)
                .collect(Collectors.toSet());
    }

    public <T> boolean isValid(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (violations.isEmpty()) {
            return true;
        }
        violations.forEach(violation -> log.warn("Validation error: {}", violation.getMessage()));
        return false;
    }
}
