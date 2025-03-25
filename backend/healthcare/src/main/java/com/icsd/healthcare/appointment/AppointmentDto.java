package com.icsd.healthcare.appointment;


import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record AppointmentDto(

        Integer id,

        LocalDate date,

        LocalTime time,

        String reason,

        LocalDateTime creationDate,

        Status status,

        Integer patientId,

        Integer doctorId
) {
}
