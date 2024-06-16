package com.icsd.healthcare.appointment;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorService;
import com.icsd.healthcare.doctoravailability.DoctorAvailabilityService;
import com.icsd.healthcare.patient.Patient;
import com.icsd.healthcare.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentMapper appointmentMapper;
    private final DoctorAvailabilityService doctorAvailabilityService;

    public String createNewAppointment(NewAppointmentDto appointmentDto) {

        Appointment appointment = appointmentMapper.mapDtoToEntity(appointmentDto);
       /* if(doctorAvailabilityService.)*/
        return "Appointment created: " + appointment.getId();

    }


}
