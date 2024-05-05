package com.icsd.healthcare.slot.mapper;

import com.icsd.healthcare.mapper.Mapper;
import com.icsd.healthcare.slot.dto.SlotDto;
import com.icsd.healthcare.slot.dto.SlotSaveDto;
import com.icsd.healthcare.slot.entity.Slot;
import org.springframework.stereotype.Service;

@Service
public interface SlotMapper extends Mapper<Slot, SlotDto> {
    Slot mapDtoToEntity(SlotSaveDto slotSaveDto);
}
