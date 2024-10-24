package com.icsd.healthcare.slot;

import com.icsd.healthcare.shared.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public interface SlotMapper extends Mapper<Slot, SlotDto> {
    Slot mapDtoToEntity(SlotDto slotDto);
}
