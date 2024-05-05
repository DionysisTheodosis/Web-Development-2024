package com.icsd.healthcare.doctoravailability.dto;

import com.icsd.healthcare.doctor.entity.Doctor;
import com.icsd.healthcare.slot.dto.SlotDto;
import com.icsd.healthcare.slot.entity.Slot;
import lombok.Builder;

@Builder
public record DoctorAvailabilityResponseDto(
        Slot slot
) {
}
