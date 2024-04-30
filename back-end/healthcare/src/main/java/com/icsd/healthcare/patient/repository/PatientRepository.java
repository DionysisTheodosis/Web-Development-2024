package com.icsd.healthcare.patient.repository;


import com.icsd.healthcare.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Patient findByAmka(String amka);
}
