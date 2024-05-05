package com.icsd.healthcare.slot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record SlotDto(
        @JsonProperty("id")
        @NotNull
        Integer id,
        @JsonProperty("slotDateTime")
        LocalDateTime slotDateTime,
        @JsonProperty("duration")
        Integer duration
) {

}
