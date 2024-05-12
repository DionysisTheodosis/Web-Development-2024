package com.icsd.healthcare.medicalhistoryrecord;


import com.icsd.healthcare.medicalhistory.MedicalHistory;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
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

    @ManyToOne
    @JoinColumn(name = "historyID")
    private MedicalHistory medicalHistory;

    @FutureOrPresent
    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "identifiedIssues")
    private String identifiedIssues;

    @Column(name = "treatment")
    private String treatment;

}
