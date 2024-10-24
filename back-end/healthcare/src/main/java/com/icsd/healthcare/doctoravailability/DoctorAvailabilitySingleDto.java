package com.icsd.healthcare.doctoravailability;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.icsd.healthcare.slot.SlotDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DoctorAvailabilitySingleDto(

            @JsonProperty("slot")
            @NotNull
            SlotDto slotDto
    )
{
}
