package com.icsd.healthcare.appointment;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorService;
import com.icsd.healthcare.patient.Patient;
import com.icsd.healthcare.patient.PatientService;
import com.icsd.healthcare.shared.utils.ApplicationConstants;
import com.icsd.healthcare.slot.SlotService;
import io.lettuce.core.ScriptOutputType;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AppointmentMapper {
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final SlotService slotService;

    public Appointment mapDtoToEntity(NewAppointmentByDateTimeDto dto) {

       return Appointment.builder()
                .time(dto.time())
                .status(Status.CREATED)
                .reason(dto.reason())
                .date(dto.date())
                .patient(getPatient(dto.patientId()))
                .doctor(getDoctor())
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

    public Appointment mapDtoToEntity(NewAppointmentBySlotDto dto) {
        LocalDateTime slotLocalDateTime = slotService.getSlotFromSlotId(dto.slotId()).getSlotDateTime();
        return Appointment.builder()
                .time(slotLocalDateTime.toLocalTime())
                .status(Status.CREATED)
                .reason(dto.reason())
                .date(slotLocalDateTime.toLocalDate())
                .patient(getPatient(dto.patientId()))
                .doctor(getDoctor())
                .build();
    }

    private Doctor getDoctor() {
        return doctorService.findDoctorById(ApplicationConstants.SINGLE_DOCTOR_ID);
    }

    private Patient getPatient(Integer patientId) {
        return patientService.findPatientById(patientId);
    }

}
