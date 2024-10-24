package com.icsd.healthcare.medicalhistoryrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalHistoryRecordRepository extends JpaRepository<MedicalHistoryRecord,Integer> {

    List<MedicalHistoryRecord> findByMedicalHistory_Patient_PatientID(Integer patientId);

    Optional<MedicalHistoryRecord> findFirstByMedicalHistory_Patient_PatientIDOrderByCreationDateDesc(Integer patientId);

    Optional<List<MedicalHistoryRecord>> findAllByMedicalHistory_Patient_PatientIDOrderByCreationDateDesc(Integer patientId);

    Optional<MedicalHistoryRecord> findByIdAndMedicalHistory_Patient_PatientID(Integer id, Integer patientId);

    List<MedicalHistoryRecord> findAll(Specification<MedicalHistoryRecord> specification);

    Page<MedicalHistoryRecord> findAll(Specification<MedicalHistoryRecord> specification, Pageable pageable);
}