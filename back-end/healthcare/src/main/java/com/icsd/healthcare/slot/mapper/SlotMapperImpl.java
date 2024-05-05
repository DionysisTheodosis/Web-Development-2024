package com.icsd.healthcare.slot.mapper;

import com.icsd.healthcare.slot.dto.SlotDto;
import com.icsd.healthcare.slot.dto.SlotSaveDto;
import com.icsd.healthcare.slot.entity.Slot;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SlotMapperImpl implements SlotMapper{

    @Override
    public Slot mapDtoToEntity(@NotNull SlotSaveDto slotSaveDto) {
       if (slotSaveDto.duration() != null) {
            return Slot.builder().slotDateTime(slotSaveDto.slotDateTime()).
                    duration(slotSaveDto.duration()).
                    build();
        } else {
            return Slot.builder().slotDateTime(
                    slotSaveDto.slotDateTime()).
                    build();
        }
    }

    @Override
    public Slot mapDtoToEntity(@NotNull SlotDto slotDto) {
        if (slotDto.duration() != null) {
            return Slot.builder().id(slotDto.id())
                    .slotDateTime(slotDto.slotDateTime()).
                    duration(slotDto.duration()).
                    build();
        } else {
            return Slot.builder().slotDateTime(
                            slotDto.slotDateTime()).
                    build();
        }
    }

    public SlotDto mapEntityToDto(@NotNull Slot slot) {
          return SlotDto.builder().
                  id(slot.getId()).
                  slotDateTime(slot.getSlotDateTime()).
                  duration(slot.getDuration()).
                  build();
    }

}