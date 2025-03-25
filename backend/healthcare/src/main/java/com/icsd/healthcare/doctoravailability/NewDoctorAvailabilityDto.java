package com.icsd.healthcare.doctoravailability;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.icsd.healthcare.slot.NewSlotDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewDoctorAvailabilityDto(

        @Valid
        @JsonProperty("slot")
        @NotNull
        NewSlotDto slotDto
)
{
}
