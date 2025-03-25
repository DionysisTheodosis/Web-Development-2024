package com.icsd.healthcare.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record DoctorOrSecretaryUpdateAppointmentDto(
        @NotBlank
        Integer id,

        @FutureOrPresent
        LocalDate date,

        @FutureOrPresent
        LocalTime time,

        @NotBlank
        String reason,

        @NotBlank
        Status status
) {
}