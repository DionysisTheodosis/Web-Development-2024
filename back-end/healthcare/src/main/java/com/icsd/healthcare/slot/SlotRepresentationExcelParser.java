package com.icsd.healthcare.slot;

import com.icsd.healthcare.shared.utils.ExcelParser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class SlotRepresentationExcelParser {
    private final ExcelParser excelParser;

    public  Set<SlotCsvRepresentation> excelParse(MultipartFile file) throws IOException {
        return this.excelParser.parseExcel(file, this::mapRowToSlotDto);
    }

    private  SlotCsvRepresentation mapRowToSlotDto(Row row) {
        SlotCsvRepresentation.SlotCsvRepresentationBuilder  slotBuilder = SlotCsvRepresentation.builder();

        CellType cellType = row.getCell(0).getCellType();

        switch (cellType) {
            case NUMERIC:
                double numericValue = row.getCell(0).getNumericCellValue();
                LocalDateTime dateTimeFromNumericValue = LocalDateTime.ofEpochSecond((long) numericValue, 0, ZoneOffset.UTC);
                slotBuilder.localDateTime(dateTimeFromNumericValue);
                break;
            case STRING:
                String stringValue = row.getCell(0).getStringCellValue();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime dateTimeFromString = LocalDateTime.parse(stringValue, formatter);
                slotBuilder.localDateTime(dateTimeFromString);
                break;
            default:
                //todo: "To give right exceptions
                throw new IllegalStateException("Unexpected value: " + cellType);
        }
        Cell durationCell = row.getCell(1);
//todo: "To implement better the code
        if (durationCell != null) {
            switch (durationCell.getCellType()) {
                case NUMERIC:
                    slotBuilder.duration((int) durationCell.getNumericCellValue());
                    break;
                case STRING:
                    slotBuilder.duration(Integer.parseInt(durationCell.getStringCellValue()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + cellType);
            }
            slotBuilder.duration((int) durationCell.getNumericCellValue());
        }
        return slotBuilder.build();
    }
}
