package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorService;
import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import com.icsd.healthcare.shared.utils.ApplicationConstants;
import com.icsd.healthcare.slot.Slot;
import com.icsd.healthcare.slot.SlotDto;
import com.icsd.healthcare.slot.SlotService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Builder
@AllArgsConstructor
@Service
@Slf4j
public class DoctorAvailabilityService {

    private final DoctorService doctorService;
    private final SlotService slotService;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;


    @Transactional
    public void saveDoctorAvailabilitySingle(NewDoctorAvailabilityDto newDoctorAvailabilityDto) {

        Slot slot = slotService.getSlotFromSlotDto(newDoctorAvailabilityDto.slotDto());

        List<Slot> slots = getSlotsFromDoctorAvailability(signInDoctorId());

        if (slots == null || slots.isEmpty() || !slotService.isSlotExistsOrOverlaps(slot, slots)) {
            slotService.save(slot);
            DoctorAvailability doctorAvailability = DoctorAvailability.builder()
                    .doctor(signInDoctor())
                    .slot(slot)
                    .build();

            doctorAvailabilityRepository.save(doctorAvailability);
        } else {
            throw new DoctorAvailabilityAlreadyExistsException(ErrorCode.ERROR_DOCTOR_AVAILABILITY_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void uploadSlotsFromFile(MultipartFile file) {

        Set<SlotDto> slotDtos = slotService.correctSlotsFromFile(file, getSlotsFromDoctorAvailability(signInDoctor().getDoctorID()));
        List<Slot> slots = slotDtos.stream()
                .map(slotService::getSlotFromSlotDto)
                .toList();
        saveDoctorAvailabilitySlots(slots, signInDoctor());

    }


    @Transactional
    public void updateDoctorAvailability(DoctorAvailabilitySingleDto doctorAvailabilityDto) {

        Slot updatedSlot = slotService.getSlotFromSlotDto(doctorAvailabilityDto.slotDto());
        slotService.validateSlotNotExistsOrOverlaps(updatedSlot, getSlotsFromDoctorAvailability(signInDoctorId()));
        System.out.println("doctor availability"+doctorAvailabilityDto);
        System.out.println("doctor id" +signInDoctorId());
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository
                .findByDoctor_DoctorIDAndSlot_Id(signInDoctorId(), updatedSlot.getId())
                .orElseThrow(DoctorAvailabilityNotFoundException::new);

        doctorAvailability.setSlot(updatedSlot);
        doctorAvailabilityRepository.save(doctorAvailability);
    }

    @Transactional
    public DoctorAvailabilityMultipleDto getDoctorAvailabilitiesByDate(LocalDate date) {

        if (date == null) {
            date = LocalDate.now();

        }

        // Set startTime to the start of the day (00:00)
        LocalDateTime startTime = date.atStartOfDay();

        // Set endTime to the end of the day (23:59:59.999999999)
        LocalDateTime endTime = date.atTime(23, 59, 59, 999999999);
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository
                .findByDoctor_DoctorIDAndSlot_SlotDateTimeBetween(ApplicationConstants.SINGLE_DOCTOR_ID, startTime, endTime)
                .orElse(Collections.emptyList());


        Set<SlotDto> slotDtos = doctorAvailabilities.stream()
                .map(doctorAvailability -> slotService.getSlotDtoFromSlot(doctorAvailability.getSlot()))
                .collect(Collectors.toSet());

        return new DoctorAvailabilityMultipleDto(slotDtos);
    }

    @Transactional
    public void deleteDoctorAvailabilityBySlotId(Integer slotId) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository
                .findByDoctor_DoctorIDAndSlot_Id(signInDoctorId(), slotId)
                .orElseThrow(DoctorAvailabilityNotFoundException::new);
        doctorAvailabilityRepository.delete(doctorAvailability);
    }


    private void saveDoctorAvailabilitySlots(List<Slot> slots, Doctor doctor) {

        List<Slot> savedSlots = slotService.saveAll(slots);


        List<DoctorAvailability> doctorAvailabilities = savedSlots.stream()
                .map(slot -> DoctorAvailability.builder()
                        .doctor(doctor)
                        .slot(slot)
                        .build())
                .toList();

        doctorAvailabilityRepository.saveAll(doctorAvailabilities);

    }

    private Doctor signInDoctor() {
        return doctorService.getAuthenticatedDoctor();
    }

    private Integer signInDoctorId() {
        return signInDoctor().getDoctorID();
    }

    private List<Slot> getSlotsFromDoctorAvailability(Integer doctorId) {
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository.findByDoctor_DoctorID(doctorId);

        return doctorAvailabilities.stream()
                .map(DoctorAvailability::getSlot)
                .toList();
    }

    public boolean isDoctorAvailable(Integer doctorId, LocalDateTime localDateTime) {
        return doctorAvailabilityRepository.existsByDoctor_DoctorIDAndSlot_SlotDateTime(doctorId, localDateTime);
    }

    public void setNotAvailable(Integer doctorId, LocalDateTime localDateTime) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository
                .findByDoctor_DoctorIDAndSlot_SlotDateTime(doctorId, localDateTime)
                .orElseThrow(DoctorAvailabilityNotFoundException::new);
        doctorAvailabilityRepository.delete(doctorAvailability);
    }

    public void setNotAvailable(Integer doctorId, Integer slotId) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository
                .findByDoctor_DoctorIDAndSlot_Id(doctorId, slotId)
                .orElseThrow(DoctorAvailabilityNotFoundException::new);
        doctorAvailabilityRepository.delete(doctorAvailability);
    }

}