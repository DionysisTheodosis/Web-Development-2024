package com.icsd.healthcare.slot;

import com.icsd.healthcare.shared.exception.ParsingFileIOException;
import com.icsd.healthcare.shared.utils.CsvParser;
import com.icsd.healthcare.shared.validators.GenericValidator;
import com.icsd.healthcare.shared.validators.ValidCsvFileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@Slf4j
@Service
public class SlotService {

    private final SlotMapperImpl slotMapperImpl;
    private SlotRepository slotRepository;
    private final GenericValidator genericValidator;
    private final CsvParser csvParser;


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

    public Slot saveSlot(SlotDto slotDto, List<Slot> existingSlots) {
        Slot slot = slotMapperImpl.mapDtoToEntity(slotDto);

        if (isSlotOverlapping(slot, existingSlots)) {
            throw new SlotNotFoundException("Slot overlaps with existing slot");
        }

        return slotRepository.save(slot);
    }

    private boolean isSlotOverlapping(Slot newSlot, List<Slot> existingSlots) {
        LocalDateTime newSlotStart = newSlot.getSlotDateTime();
        LocalDateTime newSlotEnd = newSlotStart.plusSeconds(newSlot.getDuration());

        return existingSlots.stream().anyMatch(existingSlot -> {
            LocalDateTime existingSlotStart = existingSlot.getSlotDateTime();
            LocalDateTime existingSlotEnd = existingSlotStart.plusMinutes(existingSlot.getDuration());

            return (newSlotStart.isBefore(existingSlotEnd) && existingSlotStart.isBefore(newSlotEnd)) ||
                    (existingSlotStart.isBefore(newSlotStart) && newSlotStart.isBefore(existingSlotEnd));
        });
    }

    private Set<SlotDto> returnCorrectSlots(Set<SlotDto> slotDtos, List<Slot> existingSlots) {
        return slotDtos.stream()
                .map(slotMapperImpl::mapDtoToEntity)
                .filter(slot -> !isSlotOverlapping(slot, existingSlots)&&!hasSameDateTimeAndDuration(slot, existingSlots))
                .map(slotMapperImpl::mapEntityToDto)
                .collect(Collectors.toSet());
    }

    private Set<SlotDto> mapSlotFileRepresentationToSlotDto(List<SlotFileRepresentation> slots) {
        return slots.stream()
                .map(slotMapperImpl::mapSlotFileRepresentationToSlotDto)
                .collect(Collectors.toSet());
    }

    public Set<SlotDto> correctSlotsFromCsv(@ValidCsvFileType MultipartFile file, List<Slot> existingSlots) {
        try {
            List<SlotFileRepresentation> slotsFromFile = csvParser.parseCsv(file, SlotFileRepresentation.class);
            log.info("Number of slots read: {}", slotsFromFile.size());

            Set<SlotDto> slotDtos = mapSlotFileRepresentationToSlotDto(slotsFromFile);
            Set<SlotDto> validSlotDtos = genericValidator.validateAndFilter(slotDtos);
            validSlotDtos = returnCorrectSlots(validSlotDtos, existingSlots);

            if (validSlotDtos.isEmpty()) {
                throw new SlotNotFoundException("Slot not created");
            }

            log.info("Number of valid slots read: {}", validSlotDtos.size());
            return validSlotDtos;

        } catch (IOException e) {
            log.error("Error processing file", e);
            throw new ParsingFileIOException();
        }
    }

    private boolean hasSameDateTimeAndDuration(Slot newSlot, List<Slot> existingSlots) {
       return existingSlots.stream()
               .anyMatch(slot -> slot.getSlotDateTime().equals(newSlot.getSlotDateTime())||slot.getDuration().equals(newSlot.getDuration()));
    }

    public boolean isSlotExistsOrOverlaps(Slot newSlot, List<Slot> existingSlots) {
        return hasSameDateTimeAndDuration( newSlot,existingSlots) || isSlotOverlapping(newSlot, existingSlots);
    }

    public Slot saveSlot(Slot slot) {
        if(slot.getDuration()==null || slot.getDuration()==0) {
            slot.setDuration(30);
        }
        return slotRepository.save(slot);
    }
}

