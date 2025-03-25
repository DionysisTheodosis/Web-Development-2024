package com.icsd.healthcare.patient;


import com.icsd.healthcare.medicalhistory.MedicalHistory;
import com.icsd.healthcare.shared.validators.ValidAmka;
import com.icsd.healthcare.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_patient")
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientID")
    private Integer patientID;

    @ValidAmka
    @Column(name = "amka", length = 11, columnDefinition = "CHAR", nullable = false)
    private String amka;

    @Column(name = "registrationDate", insertable = false, updatable = false, nullable = false)
    private LocalDateTime registrationDate;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    private User user;

    @ToString.Exclude
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private MedicalHistory medicalHistory;
}
