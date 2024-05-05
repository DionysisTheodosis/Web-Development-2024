package com.icsd.healthcare.medicalhistory.entity;


import com.icsd.healthcare.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "t_medicalHistory")
@Entity
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "patientID")
    private Patient patient;

}
