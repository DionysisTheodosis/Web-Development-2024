package com.icsd.healthcare.doctor;


import com.icsd.healthcare.shared.utils.ApplicationConstants;
import com.icsd.healthcare.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Table(name = "t_doctor")
@Entity
public class Doctor {
    @Id
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PUBLIC)
    @Column(name = "doctorID")
    private final  Integer doctorID = ApplicationConstants.SINGLE_DOCTOR_ID;

    @Column(name = "specialty", nullable = false, length = 100)
    private String specialty;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    private User user;

    public Doctor(String specialty,User user) {
        this.specialty = specialty;
        this.user = user;
    }

}
  