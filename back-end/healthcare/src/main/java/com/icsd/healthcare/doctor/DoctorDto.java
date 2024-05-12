package com.icsd.healthcare.doctor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record DoctorDto(

        Integer  doctorID,

        @Pattern(regexp = "[a-zA-Z]{4,}")
        String  specialty,
        @NotBlank
        Integer  userId
) {

}
