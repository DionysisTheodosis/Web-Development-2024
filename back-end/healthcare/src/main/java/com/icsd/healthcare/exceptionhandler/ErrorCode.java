package com.icsd.healthcare.exceptionhandler;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {
    public static final String ERROR_DOCTOR_AVAILABILITY_ALREADY_EXISTS = "Doctor is already available for this slot.";
    public static final String DOCTOR_NOT_EXIST_EXCEPTION = "Doctor doesn't exists with this id:";
    public static final String ERROR_SLOT_DOESNT_EXISTS = "Slot doesn't exists with this id:";
    public static final String USER_NOT_FOUND_WITH_THIS_CREDENTIALS = "User not found with this credentials.";
}
