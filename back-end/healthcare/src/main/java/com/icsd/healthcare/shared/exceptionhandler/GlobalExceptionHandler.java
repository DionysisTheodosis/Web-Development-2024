package com.icsd.healthcare.shared.exceptionhandler;

import com.icsd.healthcare.doctor.DoctorNotFoundException;
import com.icsd.healthcare.doctoravailability.DoctorAvailabilityAlreadyExistsException;
import com.icsd.healthcare.doctoravailability.DoctorAvailabilityIOExcepetion;
import com.icsd.healthcare.slot.SlotNotFoundException;
import com.icsd.healthcare.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorAvailabilityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleDoctorAvailabilityAlreadyExistsException(
            DoctorAvailabilityAlreadyExistsException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleDoctorNotExistException(
            DoctorNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(SlotNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleSlotNotExistException(
            SlotNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleUserNotFoundException(
            UserNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(DoctorAvailabilityIOExcepetion.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleDoctorAvailabilityIOExcepetion(
            DoctorAvailabilityIOExcepetion ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    }
}
