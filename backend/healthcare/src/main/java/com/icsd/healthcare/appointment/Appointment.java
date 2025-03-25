package com.icsd.healthcare.appointment;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.patient.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_appointment")
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appID")
    private Integer id;

    @FutureOrPresent
    @Column(name = "appDate", nullable = false)
    private LocalDate date;

    @FutureOrPresent
    @Column(name = "appTime",nullable = false)
    private LocalTime time;

    @Column(name = "appReason" , nullable = false)
    private String reason;

    @FutureOrPresent
    @Column(name = "appCreationDate", insertable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "appStatus")
    private Status status;

    @OneToOne
    @JoinColumn(name = "appPatientID", nullable = false)
    private Patient patient;
    @OneToOne
    @JoinColumn(name = "appDoctorID", nullable = false)
    private Doctor doctor;

    public boolean isStatusComplete(){
        return status == Status.COMPLETED;
    }
    public boolean isStatusAttended(){
        return status == Status.ATTENDED;
    }
    public boolean isStatusCreated(){
        return status == Status.CREATED;
    }
    public boolean isStatusCanceled(){
        return status == Status.CANCELED;
    }
    public boolean isStatusCompletedOrCanceled(){
        return status == Status.COMPLETED || status == Status.CANCELED;
    }
    public boolean isStatusAttendedOrCompleted(){
        return status == Status.ATTENDED || status == Status.COMPLETED;
    }
    public boolean isDoctorsAppointment(Doctor doctor) {
        return this.doctor.equals(doctor);
    }
    public boolean isDoctorsAppointment(Integer doctorId) {
        return this.doctor.getDoctorID().equals(doctorId);
    }
    public boolean isPatientsAppointment(Integer patientId) {
        return this.patient.getPatientID().equals(patientId);
    }
}
