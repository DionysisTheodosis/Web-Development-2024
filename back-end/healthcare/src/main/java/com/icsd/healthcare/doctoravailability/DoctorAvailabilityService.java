package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorService;
import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import com.icsd.healthcare.slot.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;


@Builder
@AllArgsConstructor
@Service
@Slf4j
public class DoctorAvailabilityService {

    private final DoctorService doctorService;
    private final SlotService slotService;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final SlotMapper slotMapper;
    private final SlotMapperImpl slotMapperImpl;
    private final SlotRepository slotRepository;


    public void saveDoctorAvailabilitySingle(DoctorAvailabilitySingleDto doctorAvailabilitySingleDto) {

        Slot slot = slotMapper.mapDtoToEntity(doctorAvailabilitySingleDto.slotDto());

        List<Slot> slots = getSlotsFromDoctorAvailability(signInDoctor().getDoctorID());
        if(slots==null || slots.isEmpty()){

        }
        if (slots == null || slots.isEmpty() || !slotService.isSlotExistsOrOverlaps(slot, slots)) {
            DoctorAvailability doctorAvailability = DoctorAvailability.builder()
                    .doctor(signInDoctor())
                    .slot(slot)
                    .build();

            // Save the DoctorAvailability entity
            doctorAvailabilityRepository.save(doctorAvailability);
        } else {
            throw new DoctorAvailabilityAlreadyExistsException(ErrorCode.ERROR_DOCTOR_AVAILABILITY_ALREADY_EXISTS);
        }
    }

    public void uploadSlotsAsCsv(MultipartFile file) {
            Set<SlotDto> slotDtos = slotService.correctSlotsFromCsv(file,getSlotsFromDoctorAvailability(signInDoctor().getDoctorID()));
            List<Slot> slots = slotDtos.stream()
                .map(slotMapperImpl::mapDtoToEntity)
                .toList();
            saveDoctorAvailabilitySlots(slots, signInDoctor());

    }

    private void saveDoctorAvailabilitySlots(List<Slot> slots,Doctor doctor) {

        List<DoctorAvailability> doctorAvailabilities = slots.stream()
                .map(slot -> DoctorAvailability.builder()
                        .doctor(doctor)
                        .slot(slotService.saveSlot(slot))
                        .build())
                .toList();

        doctorAvailabilityRepository.saveAll(doctorAvailabilities);
    }

    private Doctor signInDoctor(){
        return doctorService.getAuthenticatedDoctor();
    }

    private List<Slot> getSlotsFromDoctorAvailability(Integer doctorId) {
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityRepository.findByDoctor_DoctorID(doctorId);

        return doctorAvailabilities.stream()
                .map(DoctorAvailability::getSlot)
                .toList();
    }

    public Integer uploadSlotsAsExcel(MultipartFile file) {
        return null;
    }


}
