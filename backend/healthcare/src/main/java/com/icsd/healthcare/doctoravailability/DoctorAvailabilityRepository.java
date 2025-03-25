package com.icsd.healthcare.doctoravailability;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability,Integer> {

    List<DoctorAvailability> findByDoctor_DoctorID(int doctorID);
    boolean existsByDoctor_DoctorIDAndSlot_SlotDateTime(Integer doctorId, LocalDateTime localDateTime);
    Optional<DoctorAvailability> findByDoctor_DoctorIDAndSlot_SlotDateTime(Integer doctorId, LocalDateTime localDateTime);
    Optional<DoctorAvailability> findByDoctor_DoctorIDAndSlot_Id(Integer doctorId, Integer slotId);
    Optional<List<DoctorAvailability>> findByDoctor_DoctorIDAndSlot_SlotDateTimeBetween(
            Integer doctorId, LocalDateTime startTime, LocalDateTime endTime);
    Page<DoctorAvailability> findAll(Specification<DoctorAvailability> specification, Pageable pageable);
}
