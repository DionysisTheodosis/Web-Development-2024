package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorService;
import com.icsd.healthcare.patient.Patient;
import com.icsd.healthcare.patient.PatientNotFoundException;
import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import com.icsd.healthcare.slot.Slot;
import com.icsd.healthcare.slot.SlotDto;
import com.icsd.healthcare.slot.SlotCSVRepresentation;
import com.icsd.healthcare.slot.SlotService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Builder
@AllArgsConstructor
@Service
public class DoctorAvailabilityService {

    private final DoctorService doctorService;
    private final SlotService slotService;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public void saveDoctorAvailabilitySingle(DoctorAvailabilitySingleDto doctorAvailabilitySingleDto) {
        Doctor doctor = doctorService.getAuthenticatedDoctor();
        Slot slot = slotService.saveSlot(doctorAvailabilitySingleDto.slotDto());

        if (Boolean.FALSE.equals(this.doctorAvailabilityRepository.existsByDoctorAndSlot_SlotDateTime(doctor, slot.getSlotDateTime()))) {
            this.doctorAvailabilityRepository.save(DoctorAvailability
                    .builder()
                    .doctor(doctor)
                    .slot(slot)
                    .build()
            );
        } else {
            throw new DoctorAvailabilityAlreadyExistsException(ErrorCode.ERROR_DOCTOR_AVAILABILITY_ALREADY_EXISTS);
        }
    }


    public void saveDoctorAvailabilityMultiple( DoctorAvailabilityMultipleDto doctorAvailabilityMultipleDto) {
        Set<SlotDto> slots = doctorAvailabilityMultipleDto.slot();
        saveDoctorAvailabilityMultipleUtil(slots);
    }
    private void saveDoctorAvailabilityMultipleUtil(Set<SlotDto> slots){

        Doctor doctor = doctorService.getAuthenticatedDoctor();
        List<Slot> existingSlots = new ArrayList<>();

        slots.stream()
                .map(slotDto -> {
                    Slot slot = slotService.saveSlot(slotDto);
                    if (Boolean.FALSE.equals(this.doctorAvailabilityRepository.existsByDoctorAndSlot_SlotDateTime(doctor, slot.getSlotDateTime()))) {
                        return DoctorAvailability.builder().doctor(doctor).slot(slot).build();
                    } else {
                        existingSlots.add(slot);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .forEach(doctorAvailabilityRepository::save);

        if (!existingSlots.isEmpty()) {
            throw new DoctorAvailabilityAlreadyExistsException("Doctor Availability already exists for the slots: " + existingSlots);
        }
    }
    public Integer uploadSlotsAsCsv(MultipartFile file) {
        try {

            Set<SlotDto> slots = parseCsv(file);
            saveDoctorAvailabilityMultipleUtil(slots);

            return slots.size();

        } catch (IOException e) {
            throw new DoctorAvailabilityIOException();
        }

    }

    //todo: Να φτιάξω το csv
    private Set<SlotDto> parseCsv(MultipartFile file) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<SlotCSVRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(SlotCSVRepresentation.class);
            CsvToBean<SlotCSVRepresentation> csvToBean = new CsvToBeanBuilder<SlotCSVRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> {
                        SlotDto.SlotDtoBuilder slotBuilder = SlotDto.builder()
                                .slotDateTime(csvLine.getLocalDateTime());

                        if (csvLine.getDuration() != null) {
                            slotBuilder.duration(csvLine.getDuration());
                        }

                        return slotBuilder.build();
                    })
                    .collect(Collectors.toSet());

        }
    }

    public Integer uploadSlotsAsExcel(MultipartFile file) {
        return null;
    }

    public boolean checkDoctorAvailability(int doctorId){
       return doctorAvailabilityRepository.existsByDoctor_DoctorID(doctorId);
    }
}
