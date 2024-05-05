package com.icsd.healthcare.doctoravailability.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DoctorAvailabilityDto(
        @NotNull
        Integer doctorId,
        @NotNull
        Integer slotId
) {
}
