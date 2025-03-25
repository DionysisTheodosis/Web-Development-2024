package com.icsd.healthcare.appointment;

import com.icsd.healthcare.doctoravailability.DoctorAvailabilityService;
import com.icsd.healthcare.patient.PatientService;
import com.icsd.healthcare.shared.exception.InvalidOperationException;
import com.icsd.healthcare.shared.exception.InvalidParamsException;
import com.icsd.healthcare.shared.exception.UnauthorizedAccessException;
import com.icsd.healthcare.shared.utils.ApplicationConstants;
import com.icsd.healthcare.slot.SlotNotFoundException;
import com.icsd.healthcare.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final UserService userService;
    private final AppointmentMapper appointmentMapper;
    private final DoctorAvailabilityService doctorAvailabilityService;


    public Boolean isAppointmentAttendedOrCompleted(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);
        return appointment.isStatusAttendedOrCompleted();
    }

    @Transactional
    public void createAppointmentByDateTime(NewAppointmentByDateTimeDto appointmentDto) {
        LocalDateTime localDateTime = LocalDateTime.of(appointmentDto.date(), appointmentDto.time());
        if (!doctorAvailabilityService.isDoctorAvailable(ApplicationConstants.SINGLE_DOCTOR_ID, localDateTime)) {
            throw new SlotNotFoundException("Slot not found");
        }
        Appointment appointment = appointmentMapper.mapDtoToEntity(appointmentDto);
        appointment.setStatus(Status.CREATED);
        appointmentRepository.save(appointment);
        doctorAvailabilityService.setNotAvailable(ApplicationConstants.SINGLE_DOCTOR_ID, localDateTime);
    }

    @Transactional
    public void createAppointmentBySlot(NewAppointmentBySlotDto slotDto) {
        Appointment appointment = appointmentMapper.mapDtoToEntity(slotDto);
        appointment.setStatus(Status.CREATED);
        appointmentRepository.save(appointment);
        doctorAvailabilityService.setNotAvailable(ApplicationConstants.SINGLE_DOCTOR_ID, slotDto.slotId());
    }

    @Transactional
    public void cancelAppointment(Integer id) {
        Appointment appointment = getAppointmentById(id);

        if (appointment.getStatus() == Status.CREATED) {
            appointment.setStatus(Status.CANCELED);
            saveAppointment(appointment);
        } else {
            throw new InvalidOperationException("Can't cancel appointment, because the status of it is " + appointment
                    .getStatus());
        }
    }

    @Transactional
    public AppointmentDto getAppointmentDto(Integer id) {
        Appointment appointment = getAppointmentById(id);

        if (isSignInUserPatient() && !isSignInPatientsAppointment(appointment)) {
            throw new UnauthorizedAccessException();

        }
        return appointmentMapper.mapEntityToDto(appointment);
    }

    @Transactional
    public void updateAppointmentByDoctorOrSecretary(DoctorOrSecretaryUpdateAppointmentDto dto) {
        Appointment appointment = getAppointmentById(dto.id());

        checkValidAppointmentStatus(appointment);

        boolean updated = false;

        updated |= updateStatusIfNeeded(appointment, dto.status());
        updated |= updateDateIfNeeded(appointment, dto.date());
        updated |= updateTimeIfNeeded(appointment, dto.time());
        updated |= updateReasonIfNeeded(appointment, dto.reason());

        if (updated) {
            appointmentRepository.save(appointment);
        }
    }

    @Transactional
    public void updateAppointmentByPatient(PatientUpdateAppointmentDto dto) {
        Appointment appointment = getAppointmentById(dto.id());

        if (isSignInUserPatient() && !isSignInPatientsAppointment(appointment)) {
            throw new UnauthorizedAccessException();
        }

        checkValidAppointmentStatus(appointment);

        boolean updated = false;

        updated |= updateDateIfNeeded(appointment, dto.date());
        updated |= updateTimeIfNeeded(appointment, dto.time());
        updated |= updateReasonIfNeeded(appointment, dto.reason());

        if (updated) {
            appointmentRepository.save(appointment);
        }
    }

    //Doctor Secretary Search
    public Page<AppointmentDto> searchAppointments(
            String patientsLastname,
            String patientsAmka,
            Status status,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        Specification<Appointment> spec;
        if (isAllSearchParametersNull(patientsLastname, patientsAmka, status, startDate, endDate)) {
            spec = AppointmentSpecifications.todaysActiveAppointments();
            return appointmentRepository.findAll(spec, pageable).map(appointmentMapper::mapEntityToDto);
        }
        spec = AppointmentSpecifications.withFilters(
                patientsLastname, patientsAmka, status, startDate, endDate
        );

        return appointmentRepository.findAll(spec, pageable).map(appointmentMapper::mapEntityToDto);
    }

    //Patient Search
    public Page<AppointmentDto> searchOwnPatientsAppointments(
            Status status,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {
        Specification<Appointment> spec;

        Integer patientId = patientService.getAuthenticatedPatientId();
        if (isAllSearchParametersNull(status,startDate,endDate)) {
            spec = AppointmentSpecifications.todaysActiveAppointments(patientId);
            return appointmentRepository.findAll(spec, pageable).map(appointmentMapper::mapEntityToDto);
        }
        if (startDate!=null && endDate!=null && startDate.isAfter(endDate)) {
            throw new InvalidParamsException("Start time should be before end time");
        }
        spec = AppointmentSpecifications.withFilters(
               patientId, status, startDate, endDate
        );

        return appointmentRepository.findAll(spec, pageable).map(appointmentMapper::mapEntityToDto);
    }

    private Appointment getAppointmentById(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
    }

    private boolean isSignInUserPatient() {
        return userService.isSignInUserPatient();
    }

    private boolean isSignInPatientsAppointment(Appointment appointment) {
        Integer patientId = patientService.getAuthenticatedPatientId();
        return appointment.isPatientsAppointment(patientId);
    }


    private Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    private void checkValidAppointmentStatus(Appointment appointment) {
        if (appointment.isStatusCompletedOrCanceled()) {
            throw new InvalidOperationException("Can't update appointment, because the status of it is "
                    + appointment.getStatus());
        }
    }

    private boolean updateStatusIfNeeded(Appointment appointment, Status status) {
        if (appointment.getStatus() != null && !appointment.getStatus().equals(status)) {
            appointment.setStatus(status);
            return true;
        }
        return false;
    }

    private boolean updateDateIfNeeded(Appointment appointment, LocalDate date) {
        if (appointment.getDate() != null && !appointment.getDate().equals(date)) {
            appointment.setDate(date);
            return true;
        }
        return false;
    }

    private boolean updateTimeIfNeeded(Appointment appointment, LocalTime time) {
        if (appointment.getTime() != null && !appointment.getTime().equals(time)) {
            appointment.setTime(time);
            return true;
        }
        return false;
    }

    private boolean updateReasonIfNeeded(Appointment appointment, String reason) {
        if (appointment.getReason() != null && !appointment.getReason().equals(reason)) {
            appointment.setReason(reason);
            return true;
        }
        return false;
    }

    public Appointment getAppointment(Integer id) {
        return getAppointmentById(id);
    }

    private boolean isAllSearchParametersNull(
            String patientsLastname,
            String patientsAmka,
            Status status,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return patientsLastname == null && patientsAmka == null && status == null && startDate == null && endDate == null;
    }

    private boolean isAllSearchParametersNull(
            Status status,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return status == null && startDate == null && endDate == null;
    }


}
