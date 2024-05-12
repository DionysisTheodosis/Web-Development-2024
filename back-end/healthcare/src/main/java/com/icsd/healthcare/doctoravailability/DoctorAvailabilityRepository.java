package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.slot.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability,Integer> {

    Optional<DoctorAvailability> findByDoctorAndSlot(Doctor doctor, Slot slot);
    Boolean existsByDoctorAndSlot_SlotDateTime(Doctor doctor, LocalDateTime localDateTime);
}
