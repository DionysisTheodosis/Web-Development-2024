package com.icsd.healthcare.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record NewAppointmentDto(

        @FutureOrPresent
        LocalDate date,

        @FutureOrPresent
        LocalTime time,

        @NotBlank
        String reason,

        Integer patientId,

        Integer doctorId

) {
}