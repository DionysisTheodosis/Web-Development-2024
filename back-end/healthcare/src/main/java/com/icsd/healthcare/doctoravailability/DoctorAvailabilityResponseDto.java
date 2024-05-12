package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.slot.Slot;
import lombok.Builder;

@Builder
public record DoctorAvailabilityResponseDto(
        Slot slot
) {
}
