package com.icsd.healthcare.doctor;


import com.icsd.healthcare.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_doctor")
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorID")
    Integer doctorID;

    @Column(name = "specialty", nullable = false, length = 100)
    String specialty;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    private User user;
}
