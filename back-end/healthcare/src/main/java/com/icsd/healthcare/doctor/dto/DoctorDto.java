package com.icsd.healthcare.doctor.dto;

import com.icsd.healthcare.user.dto.UserDto;
import lombok.Builder;

@Builder
public record DoctorDto(
        Integer doctorID,
        String specialty,
        Integer userId
) {

}
