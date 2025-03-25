package com.icsd.healthcare.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
@Builder
public record NewAppointmentBySlotDto(
        @NotNull
        Integer slotId,

        @NotBlank
        String reason,

        @NotNull
        Integer patientId

) {
}
