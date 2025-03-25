package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.slot.Slot;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_doctorAvailability")
@IdClass(DoctorAvailabilityId.class)
public class DoctorAvailability {
    @Id
    @ManyToOne
    @JoinColumn(name = "doctorID")
    private Doctor doctor;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slotID")
    private Slot slot;
}