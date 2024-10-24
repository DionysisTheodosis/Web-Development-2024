package com.icsd.healthcare.shared.exceptionhandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {
    public static final String ERROR_DOCTOR_AVAILABILITY_ALREADY_EXISTS = "Doctor is already available for this date-time.";
    public static final String ERROR_DOCTOR_NOT_FOUND = "Doctor not found.";
    public static final String ERROR_SLOT_NOT_FOUND = "Slot not found.";
    public static final String ERROR_USER_NOT_FOUND = "User not found.";
    public static final String ERROR_PATIENT_NOT_FOUND = "Patient not found.";
    public static final String ERROR_USER_ALREADY_EXISTS = "User already exists.";
    public static final String ERROR_PATIENT_ALREADY_EXISTS = "Patient already exists.";
    public static final String ERROR_INVALID_FILE_TYPE = "File type is invalid.";
    public static final String ERROR_PARSING_FILE = "An error occurred while parsing the file.";
    public static final String ERROR_MEDICAL_HISTORY_NOT_FOUND = "Medical history not found.";
    public static final String ERROR_MEDICAL_HISTORY_SAVE = "Failed to save medical history.";
    public static final String ERROR_PATIENT_SAVE = "Failed to save medical history.";
    public static final String ERROR_MEDICAL_HISTORY_RECORD_NOT_FOUND = "Medical history record not found.";
    public static final String ERROR_ENTITY_ALREADY_EXISTS = "Entity already exists.";
    public static final String ERROR_NO_ENTRIES_PROVIDED = "Error: No entries provided.";
    public static final String ERROR_NOT_VALID_DATE_FORMAT = "Error: No valid date format should be yyyy-mm-dd.";
}
