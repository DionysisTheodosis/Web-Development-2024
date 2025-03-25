package com.icsd.healthcare.doctor;

import com.icsd.healthcare.user.UserDetailsDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DoctorInfoDto(

        Integer  doctorID,
        @NotBlank
        String  specialty,
        UserDetailsDto dto
) {
}
