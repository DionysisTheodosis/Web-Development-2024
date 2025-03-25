package com.icsd.healthcare.slot;

import com.icsd.healthcare.shared.exception.ParsingFileIOException;
import com.icsd.healthcare.shared.exception.UnsupportedFileTypeException;
import com.icsd.healthcare.shared.utils.CsvParser;
import com.icsd.healthcare.shared.validators.GenericValidator;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@Slf4j
@Service
public class SlotService {

    private final SlotMapperImpl slotMapperImpl;
    private final SlotMapper slotMapper;
    private final GenericValidator genericValidator;
    private final CsvParser csvParser;
    private final SlotRepository slotRepository;


    private boolean isSlotOverlapping(Slot newSlot, List<Slot> existingSlots) {
        LocalDateTime newSlotStart = newSlot.getSlotDateTime();
        LocalDateTime newSlotEnd = newSlotStart.plusSeconds(newSlot.getDuration()/60);

        return existingSlots.stream().anyMatch(existingSlot -> {
            LocalDateTime existingSlotStart = existingSlot.getSlotDateTime();
            LocalDateTime existingSlotEnd = existingSlotStart.plusMinutes(existingSlot.getDuration()/60);

            return (newSlotStart.isBefore(existingSlotEnd) && existingSlotStart.isBefore(newSlotEnd)) ||
                    (existingSlotStart.isBefore(newSlotStart) && newSlotStart.isBefore(existingSlotEnd));
        });
    }

    private Set<SlotDto> returnCorrectSlots(Set<SlotDto> slotDtos, List<Slot> existingSlots) {
        return slotDtos.stream()
                .filter(slotDto -> {
                    Slot slot = slotMapperImpl.mapDtoToEntity(slotDto);
                    return !isSlotOverlapping(slot, existingSlots) &&
                            !hasSameDateTimeAndDuration(slot, existingSlots);
                })
                .collect(Collectors.toSet());
    }

    private Set<SlotDto> mapSlotFileRepresentationToSlotDto(List<SlotFileRepresentation> slots) {
        return slots.stream()
                .map(slotMapperImpl::mapSlotFileRepresentationToSlotDto)
                .collect(Collectors.toSet());
    }

    public Set<SlotDto> correctSlotsFromFile(MultipartFile file, List<Slot> existingSlots) {

            List<SlotFileRepresentation> slotsFromFile = parseFile(file);
            log.info("Number of slots read: {}", slotsFromFile.size());

            Set<SlotDto> slotDtos = mapSlotFileRepresentationToSlotDto(slotsFromFile);
            Set<SlotDto> validSlotDtos = genericValidator.validateAndFilter(slotDtos);
            validSlotDtos = returnCorrectSlots(validSlotDtos, existingSlots);

            if (validSlotDtos.isEmpty()) {
                throw new SlotNotFoundException("Slots not created invalid slots or slots already exist");
            }

            log.info("Number of valid slots read: {}", validSlotDtos.size());
            return validSlotDtos;


    }

    private List<SlotFileRepresentation> parseFile(MultipartFile file) {

        String fileExtension = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();

        if ("xlsx".equals(fileExtension)) {
            try (InputStream inputStream = file.getInputStream()) {
                return Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, SlotFileRepresentation.class);
            } catch (IOException e) {
                log.error("Error processing file", e);
                throw new ParsingFileIOException();
            }

        } else if ("csv".equals(fileExtension)) {

            try {
                return csvParser.parseCsv(file, SlotFileRepresentation.class);
            }
            catch (IOException e) {
                log.error("Error processing file", e);
                throw new ParsingFileIOException();
            }
        }
        else{
            throw new UnsupportedFileTypeException();
        }
    }

    private boolean hasSameDateTimeAndDuration(Slot newSlot, List<Slot> existingSlots) {
        return existingSlots.stream()
                .anyMatch(slot -> slot.getSlotDateTime().equals(newSlot.getSlotDateTime()) && slot.getDuration().equals(newSlot.getDuration()));
    }

    public boolean isSlotExistsOrOverlaps(Slot newSlot, List<Slot> existingSlots) {
        return hasSameDateTimeAndDuration(newSlot, existingSlots) || isSlotOverlapping(newSlot, existingSlots);
    }

    public void validateSlotNotExistsOrOverlaps(Slot newSlot, List<Slot> existingSlots) {
        if (isSlotExistsOrOverlaps(newSlot, existingSlots)) {
            throw new SlotAlreadyExists("Slot already exists");
        }
    }

    public Slot getSlotFromSlotDto(NewSlotDto slotDto) {
        return slotMapperImpl.mapDtoToEntity(slotDto);
    }
    public Slot getSlotFromSlotDto(SlotDto slotDto) {
        return slotMapper.mapDtoToEntity(slotDto);
    }

    public SlotDto getSlotDtoFromSlot(Slot slot) {
        return slotMapper.mapEntityToDto(slot);
    }


    public void save(Slot slot) {
        slotRepository.save(slot);
    }

    public Slot getSlotFromSlotId(Integer slotId) {
       return slotRepository.findById(slotId).orElseThrow(SlotNotFoundException::new);
    }

    public List<Slot> saveAll(List<Slot> slots) {
       return slotRepository.saveAll(slots);
    }
}

