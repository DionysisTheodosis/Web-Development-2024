package com.icsd.healthcare.doctor;


import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    public Doctor findDoctorByEmail(String email) {
        log.info("Find doctor by email: {}", email);
        return doctorRepository.findByEmail(email).orElseThrow( ()-> new DoctorNotFoundException(ErrorCode.ERROR_DOCTOR_NOT_FOUND +" With email: " + email) );

    }

}
