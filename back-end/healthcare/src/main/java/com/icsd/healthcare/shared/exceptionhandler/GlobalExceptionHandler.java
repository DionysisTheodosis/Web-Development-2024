package com.icsd.healthcare.shared.exceptionhandler;

import com.icsd.healthcare.doctor.DoctorNotFoundException;
import com.icsd.healthcare.doctoravailability.DoctorAvailabilityAlreadyExistsException;
import com.icsd.healthcare.doctoravailability.DoctorAvailabilityIOException;
import com.icsd.healthcare.medicalhistory.MedicalHistoryNotFoundException;
import com.icsd.healthcare.medicalhistory.MedicalHistorySaveException;
import com.icsd.healthcare.medicalhistoryrecord.MedicalHistoryRecordNotFoundException;
import com.icsd.healthcare.patient.PatientAlreadyExistsException;
import com.icsd.healthcare.patient.PatientNotFoundException;
import com.icsd.healthcare.patient.PatientSaveException;
import com.icsd.healthcare.shared.exception.*;
import com.icsd.healthcare.shared.security.RegistrationException;
import com.icsd.healthcare.slot.SlotNotFoundException;
import com.icsd.healthcare.user.UserAlreadyExistsException;
import com.icsd.healthcare.user.UserNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorAvailabilityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleDoctorAvailabilityAlreadyExistsException(
            DoctorAvailabilityAlreadyExistsException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleDoctorNotExistException(
            DoctorNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handlePatientNotFoundException(
            PatientNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(SlotNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleSlotNotExistException(
            SlotNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleUserNotFoundException(
            UserNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(DoctorAvailabilityIOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleDoctorAvailabilityIOException(
            DoctorAvailabilityIOException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    }

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleRegistrationException(
            RegistrationException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
            String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        log.info(ex.getMessage());
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), "Data Inntegrity Violation");
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleOptimisticLockException(OptimisticLockException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), "Optimistic locking failure");
    }

    @ExceptionHandler(InvalidSortByParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleInvalidSortByParameterException(InvalidSortByParameterException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handlePatientAlreadyExistsException(PatientAlreadyExistsException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(NoChangesDetectedException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public @ResponseBody ErrorResponse handleNoChangesDetectedException(NoChangesDetectedException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_MODIFIED.value(), ex.getMessage());
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleInvalidFileTypeException(InvalidFileTypeException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler(ParsingFileIOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleParsingFileIOException(ParsingFileIOException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler(MedicalHistoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleMedicalHistoryNotFoundException(
            MedicalHistoryNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(MedicalHistorySaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleMedicalHistorySaveException(MedicalHistorySaveException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(PatientSaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handlePatientSaveException(PatientSaveException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler(MedicalHistoryRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleMedicalHistoryRecordNotFoundException(
            MedicalHistoryRecordNotFoundException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleEntityAlreadyExistsException(
            EntityAlreadyExistsException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(NoEntriesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleNoEntriesException(
            NoEntriesException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
    @ExceptionHandler(DateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleDateFormatException(
            DateFormatException ex) {
        log.info(ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
