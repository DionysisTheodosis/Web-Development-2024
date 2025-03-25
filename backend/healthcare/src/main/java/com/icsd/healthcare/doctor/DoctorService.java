package com.icsd.healthcare.doctor;


import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import com.icsd.healthcare.shared.utils.ApplicationConstants;
import com.icsd.healthcare.shared.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final AuthUtils authUtil;
    private final DoctorMapper doctorMapper;

    public Doctor findDoctorByEmail(String email) {
        log.info("Find doctor by email: {}", email);
        return doctorRepository.findByEmail(email).orElseThrow( ()-> new DoctorNotFoundException(ErrorCode.ERROR_DOCTOR_NOT_FOUND +" With email: " + email) );

    }

    public Optional<Doctor> saveDoctor(Doctor doctor) {
        return Optional.of(doctorRepository.save(doctor));
    }

    public Doctor getAuthenticatedDoctor(){
        String email = authUtil.getAuthenticatedUserEmail();
        return doctorRepository.findByUser_Email(email).orElseThrow(()->new DoctorNotFoundException(ErrorCode.ERROR_DOCTOR_NOT_FOUND));
    }

    public Optional<Doctor> getOptionalAuthenticatedDoctor(){
        String email = authUtil.getAuthenticatedUserEmail();
        return doctorRepository.findByUser_Email(email);
    }

    public Doctor findDoctorById(int doctorId) {
     return doctorRepository.findById(doctorId).orElseThrow(()->new DoctorNotFoundException(ErrorCode.ERROR_DOCTOR_NOT_FOUND));
    }

    public Integer getAuthenticatedDoctorId(){
        return getAuthenticatedDoctor().getDoctorID();
    }

    public DoctorInfoDto getDoctorInfo() {
        return doctorMapper.mapEntityToInfoDto(findDoctorById(ApplicationConstants.SINGLE_DOCTOR_ID));
    }

    public DoctorInfoDto updateDoctorInfo(DoctorInfoDto doctorInfoDto) {

        Doctor doctor = getAuthenticatedDoctor();

        boolean updated = false;

        updated |= updateSpecialityIfNeeded(doctor,doctorInfoDto.specialty());
        updated |= updateNameIfNeeded(doctor,doctorInfoDto.dto().firstName());
        updated |= updateLastNameIfNeeded(doctor, doctorInfoDto.dto().lastName());
        updated |= updateEmailIfNeeded(doctor, doctorInfoDto.dto().email());
        updated |= updatePersonalIdIfNeeded(doctor, doctorInfoDto.dto().personalID());

        if (updated) {
           doctor = doctorRepository.save(doctor);
        }
        return doctorMapper.mapEntityToInfoDto(doctor);
    }

    private boolean updatePersonalIdIfNeeded(Doctor doctor, String personalId) {
        if(null!=doctor.getUser().getPersonalID() && null!=personalId && !personalId.equals(doctor.getUser().getPersonalID())){
            doctor.getUser().setPersonalID(personalId);
            return true;
        }
        return false;
    }

    private boolean updateEmailIfNeeded(Doctor doctor, String email) {
        if(null!=doctor.getUser().getEmail() && null!=email && !email.equals(doctor.getUser().getEmail())){
            doctor.getUser().setEmail(email);
            return true;
        }
        return false;
    }

    private boolean updateLastNameIfNeeded(Doctor doctor, String lastname) {
        if(null!=doctor.getUser().getLastName() && null!=lastname && !lastname.equals(doctor.getUser().getLastName())){
            doctor.getUser().setLastName(lastname);
            return true;
        }
        return false;
    }

    private boolean updateNameIfNeeded(Doctor doctor, String firstName) {
        if(null!=doctor.getUser().getFirstName() && null!=firstName && !firstName.equals(doctor.getUser().getFirstName())){
            doctor.getUser().setFirstName(firstName);
            return true;
        }
        return false;
    }

    private boolean updateSpecialityIfNeeded(Doctor doctor, String specialty) {
        if(null!=doctor.getSpecialty() && null!=specialty && !specialty.equals(doctor.getSpecialty())){
            doctor.setSpecialty(specialty);
            return true;
        }
        return false;
    }


}
