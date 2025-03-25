package com.icsd.healthcare.slot;

import com.opencsv.bean.CsvBindByName;
import com.poiji.annotation.ExcelCellName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SlotFileRepresentation {


    @ExcelCellName("Date")
    @CsvBindByName(column = "Date")
    private String date;

    @ExcelCellName("Time")
    @CsvBindByName(column = "Time")
    private String time;

    @Min(0)
    @Nullable
    @ExcelCellName("Duration")
    @CsvBindByName(column = "Duration")
    private Integer duration;

    public LocalDateTime getLocalDateTime() {
        // Ensure date and time are not null or empty before combining
        if (date == null || time == null || date.isEmpty() || time.isEmpty()) {
            throw new IllegalArgumentException("Date or Time cannot be null or empty");
        }

        // Combine Date and Time into one string (e.g., "2025-02-01T14:40:52")
        String dateTimeString = date + "T" + time;

        try {
            // Parse into LocalDateTime object using the ISO format
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Date-Time format: " + dateTimeString, e);
        }
    }
}

