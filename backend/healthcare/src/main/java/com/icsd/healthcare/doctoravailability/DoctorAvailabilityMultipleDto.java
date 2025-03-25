package com.icsd.healthcare.doctoravailability;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.icsd.healthcare.slot.SlotDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@Builder
public record DoctorAvailabilityMultipleDto(

        @JsonProperty("slots")
        @NotNull
        Set<SlotDto> slots
) {
}
