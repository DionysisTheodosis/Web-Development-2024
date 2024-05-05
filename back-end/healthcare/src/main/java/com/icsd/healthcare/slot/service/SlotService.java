package com.icsd.healthcare.slot.service;

import com.icsd.healthcare.slot.dto.SlotDto;
import com.icsd.healthcare.slot.dto.SlotSaveDto;
import com.icsd.healthcare.slot.mapper.SlotMapperImpl;
import com.icsd.healthcare.slot.repository.SlotRepository;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Service
public class SlotService {

    private final SlotMapperImpl slotMapperImpl;
    private SlotRepository slotRepository;

    public void saveSlot(SlotSaveDto slotSaveDto) {
        slotRepository.save(slotMapperImpl.mapDtoToEntity(slotSaveDto));
    }


    public List<SlotDto> getAllSlots() {
        return slotRepository.findAll().stream()
                .map(slotMapperImpl::mapEntityToDto)
                .toList();
    }

    public Page<SlotDto> findAllByPage(Pageable pageable) {
       return slotRepository.findAll(pageable)
                .map(slotMapperImpl::mapEntityToDto);
    }

    public void deleteSlot(SlotDto slotDto) {
        slotRepository.deleteById(slotMapperImpl.mapDtoToEntity(slotDto).getId());
    }
}
