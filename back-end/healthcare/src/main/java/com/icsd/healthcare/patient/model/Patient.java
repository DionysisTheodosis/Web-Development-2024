package com.icsd.healthcare.patient.model;


import com.icsd.healthcare.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
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

    @Column(name = "amka",length = 11,columnDefinition = "CHAR",nullable = false)
    private String amka;

    @Column(name = "registrationDate" , insertable = false, updatable = false,nullable = false)
    private LocalDate registrationDate;

    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDate.now();
    }
}
