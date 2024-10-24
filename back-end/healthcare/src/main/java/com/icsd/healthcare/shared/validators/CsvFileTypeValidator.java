package com.icsd.healthcare.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class CsvFileTypeValidator implements ConstraintValidator<ValidCsvFileType, MultipartFile> {

    @Override
    public void initialize(ValidCsvFileType constraintAnnotation) {
        //It's empty because we don't need it
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.getContentType() == null) {
            return false;
        }

        String contentType = file.getContentType();
        return "text/csv".equals(contentType) || "application/csv".equals(contentType);
    }
}
