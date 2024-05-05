package com.icsd.healthcare.doctoravailability.dto;

import com.icsd.healthcare.doctor.dto.DoctorDto;
import com.icsd.healthcare.slot.dto.SlotDto;
import jakarta.validation.constraints.NotNull;

public record DoctorAvailabilityFullDto(
        @NotNull
        DoctorDto doctorDto,
        @NotNull
        SlotDto slotDto

) {
}
