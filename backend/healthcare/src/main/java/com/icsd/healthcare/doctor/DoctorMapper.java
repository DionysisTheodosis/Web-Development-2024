package com.icsd.healthcare.doctor;

import com.icsd.healthcare.shared.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public interface DoctorMapper extends Mapper<Doctor, DoctorDto> {
    public DoctorDto doctorToDoctorDto(Doctor doctor);
    public Doctor doctorDtoToDoctor(DoctorDto doctorDto);

    DoctorInfoDto mapEntityToInfoDto(Doctor entity);
}
