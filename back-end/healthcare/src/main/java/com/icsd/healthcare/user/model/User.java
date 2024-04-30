package com.icsd.healthcare.user.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Integer userID;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pass", nullable = false)
    private String password;

    @Column(name = "personalID", nullable = false, length = 8, columnDefinition="CHAR")
    private String personalID;

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole", nullable = false,columnDefinition = "enum('PATIENT','DOCTOR','secretary')")
    private UserRole userRole;

}
