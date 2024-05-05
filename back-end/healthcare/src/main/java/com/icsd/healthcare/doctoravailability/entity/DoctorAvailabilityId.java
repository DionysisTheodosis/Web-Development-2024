package com.icsd.healthcare.doctoravailability.entity;

import com.icsd.healthcare.doctor.entity.Doctor;
import com.icsd.healthcare.slot.entity.Slot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAvailabilityId {
    private Slot slot;
    private Doctor doctor;
}
