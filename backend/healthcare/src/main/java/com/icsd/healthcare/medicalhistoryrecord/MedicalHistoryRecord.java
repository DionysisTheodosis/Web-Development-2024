package com.icsd.healthcare.medicalhistoryrecord;


import com.icsd.healthcare.appointment.Appointment;
import com.icsd.healthcare.medicalhistory.MedicalHistory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_medicalHistoryRecord")
@Entity
public class MedicalHistoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordID")
    private Integer id;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "historyID",referencedColumnName = "historyID")
    private MedicalHistory medicalHistory;


    @Column(name = "creationDate", nullable = false, insertable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "identifiedIssues")
    private String identifiedIssues;

    @Column(name = "treatment")
    private String treatment;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = true)  // nullable = true
    private Appointment appointment;

}
