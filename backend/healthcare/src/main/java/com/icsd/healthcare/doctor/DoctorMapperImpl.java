package com.icsd.healthcare.doctor;

import com.icsd.healthcare.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DoctorMapperImpl implements DoctorMapper {
    private final UserMapper userMapper;

    @Override
    public DoctorDto doctorToDoctorDto(Doctor doctor) {
        return null;
    }

    @Override
    public Doctor doctorDtoToDoctor(DoctorDto doctorDto) {
        return null;
    }

    @Override
    public Doctor mapDtoToEntity(DoctorDto dto) {
        return null;
    }

    @Override
    public DoctorDto mapEntityToDto(Doctor entity) {
        return null;
    }

    @Override
    public DoctorInfoDto mapEntityToInfoDto(Doctor entity) {
        return DoctorInfoDto.builder()
                .doctorID(entity.getDoctorID())
                .specialty(entity.getSpecialty())
                .dto(userMapper.userEntityToUserDetailsDto(entity.getUser()))
                .build();
    }
}
//Todo to delete unused doctor mappers