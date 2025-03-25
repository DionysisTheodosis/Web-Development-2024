package com.icsd.healthcare.patient;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Optional<Patient> findByAmka(String amka);
    Optional<Patient> findByUser_Email(String email);
    Optional<Patient> findByPatientID(Integer patientID);
    Page<Patient> findByUser_lastNameStartingWithIgnoreCase(String lastName, Pageable pageable);
    Page<Patient> findByAmkaStartingWith(String amka, Pageable pageable);
    Page<Patient> findByUser_lastNameStartingWithIgnoreCaseAndAmkaStartingWith(String lastName, String amka, Pageable pageable);
    boolean existsByAmka(String amka);
    Optional<Patient> findByUser_Id(Integer id);

}
