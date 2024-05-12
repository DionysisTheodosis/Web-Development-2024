package com.icsd.healthcare.slot;

import lombok.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@Service
public class SlotService {

    private final SlotMapperImpl slotMapperImpl;
    private SlotRepository slotRepository;

    public Slot saveSlot(SlotDto slotDto) {

        return slotRepository.save(slotMapperImpl.mapDtoToEntity(slotDto));

    }
    public void saveSlots(Set<Slot> slots) {
        this.slotRepository.saveAll(slots);
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
