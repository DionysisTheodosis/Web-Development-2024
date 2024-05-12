package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorService;
import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import com.icsd.healthcare.slot.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    public void saveDoctorAvailabilitySingle(HttpServletRequest request, DoctorAvailabilitySingleDto doctorAvailabilitySingleDto) {

        Doctor doctor = doctorService.findDoctorByEmail(request.getUserPrincipal().getName());

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


    public void saveDoctorAvailabilityMultiple(HttpServletRequest request, DoctorAvailabilityMultipleDto doctorAvailabilityMultipleDto) {
        Set<SlotDto> slots = doctorAvailabilityMultipleDto.slot();
        saveDoctorAvailabilityMultipleUtil(request,slots);
    }
    private void saveDoctorAvailabilityMultipleUtil(HttpServletRequest request, Set<SlotDto> slots){
        Doctor doctor = doctorService.findDoctorByEmail(request.getUserPrincipal().getName());
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
    public Integer uploadSlotsAsCsv(HttpServletRequest request,MultipartFile file) {
        try {

            Set<SlotDto> slots = parseCsv(file);
            saveDoctorAvailabilityMultipleUtil(request,slots);

            return slots.size();

        } catch (IOException e) {
            throw new DoctorAvailabilityIOExcepetion();
        }

    }

    private Set<SlotDto> parseCsv(MultipartFile file) throws IOException {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<SlotCsvRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(SlotCsvRepresentation.class);
            CsvToBean<SlotCsvRepresentation> csvToBean = new CsvToBeanBuilder<SlotCsvRepresentation>(reader)
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

    public Integer uploadSlotsAsExcel(HttpServletRequest request, MultipartFile file) {
        try {
            Set<SlotDto> slotDtos = SlotRepresentationExcelParser.parseExcel(file).stream().map(csvLine -> {
                SlotDto.SlotDtoBuilder slotBuilder = SlotDto.builder()
                        .slotDateTime(csvLine.getLocalDateTime());

                if (csvLine.getDuration() != null) {
                    slotBuilder.duration(csvLine.getDuration());
                }

                return slotBuilder.build();
            }).collect(Collectors.toSet());

            saveDoctorAvailabilityMultipleUtil(request,slotDtos);
            return slotDtos.size();
        }catch (IOException ex){
            throw new DoctorAvailabilityIOExcepetion();
        }
    }
}
