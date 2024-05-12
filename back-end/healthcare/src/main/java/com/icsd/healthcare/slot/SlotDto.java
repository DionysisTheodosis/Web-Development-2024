package com.icsd.healthcare.slot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SlotDto(
        @JsonProperty("id")
        Integer id,
        @NotNull
        @FutureOrPresent
        @JsonProperty("slotDateTime")
        LocalDateTime slotDateTime,
        @JsonProperty("duration")
        Integer duration
) {

}