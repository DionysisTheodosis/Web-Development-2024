package com.icsd.healthcare.patient;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Optional<Patient> findByAmka(String amka);
    Optional<Patient> findByUser_Email(String email);
    Optional<Patient> findByPatientID(Integer patientID);
    Page<PatientInfoDto> findByUser_LastNameContainingIgnoreCase(String lastName, Pageable pageable);
    boolean existsByAmka(String amka);


}
