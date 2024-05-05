package com.icsd.healthcare.doctoravailability.repository;

import com.icsd.healthcare.doctor.entity.Doctor;
import com.icsd.healthcare.doctoravailability.entity.DoctorAvailability;
import com.icsd.healthcare.slot.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability,Integer> {

   // boolean existsBySlotIdAndDoctorId(Optional<Slot> slotId, Integer doctorId);
    Optional<DoctorAvailability> findByDoctorAndSlot(Doctor doctor, Slot slot);
    /* Set<LocalDateTime> getAllBySlot(LocalDateTime currentTime);*/
}
