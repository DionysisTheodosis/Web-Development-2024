package com.icsd.healthcare.shared.exceptionhandler;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {
    public static final String ERROR_DOCTOR_AVAILABILITY_ALREADY_EXISTS = "Doctor is already available for this date-time.";
    public static final String ERROR_DOCTOR_NOT_FOUND = "Doctor not found.";
    public static final String ERROR_SLOT_NOT_FOUND = "Slot not found.";
    public static final String ERROR_USER_NOT_FOUND = "User not found.";
}
