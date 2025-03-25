package com.icsd.healthcare.medicalhistory;


import com.icsd.healthcare.medicalhistoryrecord.MedicalHistoryRecord;
import com.icsd.healthcare.patient.Patient;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@ToString
@Getter
@Setter
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

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "patientID")
    private Patient patient;

    @ToString.Exclude
    @OneToMany(mappedBy = "medicalHistory",cascade = {CascadeType.PERSIST},orphanRemoval = true)
    private Set<MedicalHistoryRecord> medicalHistoryRecords;


}
