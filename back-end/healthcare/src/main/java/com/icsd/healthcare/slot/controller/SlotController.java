package com.icsd.healthcare.slot.controller;

import com.icsd.healthcare.slot.dto.SlotDto;
import com.icsd.healthcare.slot.dto.SlotSaveDto;
import com.icsd.healthcare.slot.service.SlotService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Builder
@RestController
@RequestMapping("/slot")
public class SlotController {

    private final SlotService slotService;
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<SlotDto>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    @GetMapping("/byPage")
    public Page<SlotDto> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int sizePerPage  ,
                                       @RequestParam(defaultValue = "slotDateTime") String sortBy,
                                       @RequestParam(defaultValue = "desc") String sortOrder) {

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, sizePerPage, direction, sortBy);
        return slotService.findAllByPage(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveSlot(@RequestBody @Valid SlotSaveDto slotSaveDto){
        slotService.saveSlot(slotSaveDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> deleteSlotById(@RequestBody @Valid SlotDto slotDto){
        slotService.deleteSlot(slotDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
