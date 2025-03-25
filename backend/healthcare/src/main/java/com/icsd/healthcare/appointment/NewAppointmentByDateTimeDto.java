package com.icsd.healthcare.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record NewAppointmentByDateTimeDto(

        @FutureOrPresent
        LocalDate date,

        @FutureOrPresent
        LocalTime time,

        @NotBlank
        String reason,
        @NotNull
        Integer patientId

) {
}
