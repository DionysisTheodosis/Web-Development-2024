package com.icsd.healthcare.slot;

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
    public ResponseEntity<Page<SlotDto>> findAllByPage(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int sizePerPage,
                                                       @RequestParam(defaultValue = "slotDateTime") String sortBy,
                                                       @RequestParam(defaultValue = "desc") String sortOrder) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, sizePerPage, direction,sortBy);
        return ResponseEntity.accepted().body(slotService.findAllByPage(pageable));
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveSlot(@RequestBody @Valid SlotDto slotDto) {
        slotService.saveSlot(slotDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> deleteSlotById(@RequestBody @Valid SlotDto slotDto) {
        slotService.deleteSlot(slotDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
