package com.icsd.healthcare.doctor;

import com.icsd.healthcare.user.UserDto;

public record DoctorFullInfoDto(
        Integer doctorID,
        String specialty,
        UserDto userDto
) {

}
