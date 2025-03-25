package com.icsd.healthcare.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    @Query("SELECT a FROM Appointment a WHERE a.patient.patientID = :patientId ORDER BY a.creationDate DESC")
    Optional<Appointment> findLatestAppointmentByPatientID(@Param("patientId") Integer patientId);


    Optional<Appointment>findByIdAndPatient_PatientID(Integer id, Integer patientId);

    List<Appointment> findAll(Specification<Appointment> specification);
    Page<Appointment> findAll(Specification<Appointment> specification, Pageable pageable);
}
