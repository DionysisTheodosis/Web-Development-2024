package com.icsd.healthcare.doctor;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DoctorDto(

        Integer  doctorID,
        @NotBlank
        String  specialty,
        @NotBlank
        Integer  userId
) {

}
