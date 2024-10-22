package com.icsd.healthcare.slot;

import com.icsd.healthcare.patient.NewPatientDto;
import com.icsd.healthcare.patient.PatientFileRepresentation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SlotMapperImpl implements SlotMapper{

    @Override
    public Slot mapDtoToEntity(@NotNull SlotDto slotDto) {
       if (slotDto.duration() != null) {
            return Slot.builder().slotDateTime(slotDto.slotDateTime()).
                    duration(slotDto.duration()).
                    build();
        } else {
            return Slot.builder().slotDateTime(
                    slotDto.slotDateTime()).
                    build();
        }
    }


    public SlotDto mapEntityToDto(@NotNull Slot slot) {
          return com.icsd.healthcare.slot.SlotDto.builder().
                  id(slot.getId()).
                  slotDateTime(slot.getSlotDateTime()).
                  duration(slot.getDuration()).
                  build();
    }

    public SlotDto mapSlotFileRepresentationToSlotDto(SlotFileRepresentation slot) {

        return SlotDto.builder()
                .slotDateTime(slot.getLocalDateTime())
                .duration(slot.getDuration())
                .build();
    }
}