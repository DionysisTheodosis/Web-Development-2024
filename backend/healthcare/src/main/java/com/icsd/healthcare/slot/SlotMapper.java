package com.icsd.healthcare.slot;

import com.icsd.healthcare.shared.mapper.Mapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface SlotMapper extends Mapper<Slot, SlotDto> {
    Slot mapDtoToEntity(SlotDto slotDto);

    SlotDto mapEntityToDto(@NotNull Slot slot);
}
