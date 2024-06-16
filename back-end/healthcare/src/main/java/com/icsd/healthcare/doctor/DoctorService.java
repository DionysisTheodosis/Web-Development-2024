package com.icsd.healthcare.doctor;


import com.icsd.healthcare.patient.Patient;
import com.icsd.healthcare.patient.PatientNotFoundException;
import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor findDoctorByEmail(String email) {
        log.info("Find doctor by email: {}", email);
        return doctorRepository.findByEmail(email).orElseThrow( ()-> new DoctorNotFoundException(ErrorCode.ERROR_DOCTOR_NOT_FOUND +" With email: " + email) );

    }
    public Optional<Doctor> saveDoctor(Doctor doctor) {
        return Optional.of(doctorRepository.save(doctor));
    }

    public Doctor getAuthenticatedDoctor(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return doctorRepository.findByUser_Email(email).orElseThrow(PatientNotFoundException::new);
    }
    public Optional<Doctor> getOptionalAuthenticatedDoctor(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return doctorRepository.findByUser_Email(email);
    }
    public Doctor findDoctorById(int doctorId) {
     return doctorRepository.findById(doctorId).orElseThrow(()->new DoctorNotFoundException(ErrorCode.ERROR_DOCTOR_NOT_FOUND));
    }

}
