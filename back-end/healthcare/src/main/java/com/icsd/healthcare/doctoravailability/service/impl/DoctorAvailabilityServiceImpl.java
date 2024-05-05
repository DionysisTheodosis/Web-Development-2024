package com.icsd.healthcare.doctoravailability.service.impl;

import com.icsd.healthcare.doctor.entity.Doctor;
import com.icsd.healthcare.doctor.exception.DoctorNotExistException;
import com.icsd.healthcare.doctor.repository.DoctorRepository;
import com.icsd.healthcare.doctoravailability.dto.DoctorAvailabilityDto;
import com.icsd.healthcare.doctoravailability.dto.DoctorAvailabilityResponseDto;
import com.icsd.healthcare.doctoravailability.entity.DoctorAvailability;
import com.icsd.healthcare.doctoravailability.exceptions.DoctorAvailabilityAlreadyExistsException;
import com.icsd.healthcare.doctoravailability.repository.DoctorAvailabilityRepository;
import com.icsd.healthcare.doctoravailability.service.DoctorAvailabilityService;
import com.icsd.healthcare.exceptionhandler.ErrorCode;
import com.icsd.healthcare.slot.entity.Slot;
import com.icsd.healthcare.slot.exception.SlotNotExistException;
import com.icsd.healthcare.slot.repository.SlotRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Builder
@Data
@AllArgsConstructor
@Service
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {
    private DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;

    /*    @Override
        public AllDoctorAvailabilityResponseDto findAvailableSlots() {
            AllDoctorAvailabilityResponseDto responseDto = new AllDoctorAvailabilityResponseDto();

            Set<LocalDateTime> availableDates = new HashSet<>();

            Iterable<Slot> slots = doctorAvailabilityRepository.findAll();

            for (Slot slot : slots) {
                availableDates.add(slot.getSlotDateTime());
            }

            responseDto.setAvailableDates(availableDates);

            return responseDto;
        }*/
    public DoctorAvailabilityResponseDto saveDoctorAvailability(DoctorAvailabilityDto doctorAvailabilityDto) {

        Doctor doctor = doctorRepository.findById(doctorAvailabilityDto.doctorId())
                .orElseThrow(() -> new DoctorNotExistException(ErrorCode.DOCTOR_NOT_EXIST_EXCEPTION+ doctorAvailabilityDto.doctorId()));

        Slot slot = slotRepository.findById(doctorAvailabilityDto.slotId())
                .orElseThrow(() -> new SlotNotExistException(ErrorCode.ERROR_SLOT_DOESNT_EXISTS+ doctorAvailabilityDto.slotId()));

        Optional<DoctorAvailability> existingAvailability = doctorAvailabilityRepository.findByDoctorAndSlot(doctor, slot);
        if (existingAvailability.isPresent()) {
            throw new DoctorAvailabilityAlreadyExistsException(ErrorCode.ERROR_DOCTOR_AVAILABILITY_ALREADY_EXISTS);
        }
        DoctorAvailability doctorAvailability = DoctorAvailability.builder()
                .doctor(doctor)
                .slot(slot)
                .build();

        doctorAvailabilityRepository.
                save(doctorAvailability);


        return DoctorAvailabilityResponseDto.builder()
                .slot(doctorAvailability.getSlot())
                .build();
    }


    /*@Override
    public boolean isSlotAvailable(DoctorAvailabilityDto doctorAvailabilityDto) {

        return doctorAvailabilityRepository.existsBySlotIdAndDoctorId(
                slotRepository.findById(doctorAvailabilityDto.slotId()),
                doctorAvailabilityDto.doctorId());

    }*/



}
