package com.icsd.healthcare.slot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NewSlotDto(

        @NotNull
        @FutureOrPresent
        @JsonProperty("slotDateTime")
        LocalDateTime slotDateTime,

        @Min(value=0,message = "the duration must be greater than 0")
        @Nullable
        @JsonProperty(value = "duration",defaultValue = "1800")
        Integer duration
) {

}
