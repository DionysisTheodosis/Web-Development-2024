package com.icsd.healthcare.medicalhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory,Integer> {

    Optional<MedicalHistory> findByPatient_PatientID(Integer patientId);

}
