package com.icsd.healthcare.patient;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Patient findByAmka(String amka);
}
