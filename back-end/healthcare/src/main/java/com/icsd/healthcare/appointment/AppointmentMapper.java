package com.icsd.healthcare.appointment;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorService;
import com.icsd.healthcare.patient.Patient;
import com.icsd.healthcare.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppointmentMapper {
    private final DoctorService doctorService;
    private final PatientService patientService;

    public Appointment mapDtoToEntity(NewAppointmentDto dto) {

       return Appointment.builder()
                .time(dto.time())
                .status(Status.CREATED)
                .reason(dto.reason())
                .date(dto.date())
                .patient(getPatient(dto))
                .doctor(getDoctor(dto))
                .build();
    }


    public AppointmentDto mapEntityToDto(Appointment entity) {

        return AppointmentDto.builder()
                .id(entity.getId())
                .doctorId(entity.getDoctor().getDoctorID())
                .patientId(entity.getPatient().getPatientID())
                .time(entity.getTime())
                .status(entity.getStatus())
                .reason(entity.getReason())
                .date(entity.getDate())
                .creationDate(entity.getCreationDate())
                .build();
    }


    private Doctor getDoctor(NewAppointmentDto dto){
        if(doctorService.getOptionalAuthenticatedDoctor().isPresent()){
            return doctorService.getAuthenticatedDoctor();
        }
        return doctorService.findDoctorById(dto.doctorId());
    }

    private Patient getPatient(NewAppointmentDto dto){
        if(patientService.getOptionalAuthenticatedPatient().isPresent()){
            return patientService.getAuthenticatedPatient();
        }
        return patientService.findPatientById(dto.patientId());
    }
}
