package com.icsd.healthcare.doctor.dto;

import com.icsd.healthcare.user.dto.UserDto;

public record DoctorFullInfoDto(
        Integer doctorID,
        String specialty,
        UserDto userDto
) {

}
