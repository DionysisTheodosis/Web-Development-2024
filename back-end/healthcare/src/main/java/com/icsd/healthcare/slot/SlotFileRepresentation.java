package com.icsd.healthcare.slot;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.poiji.annotation.ExcelCellName;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SlotFileRepresentation {

    @ExcelCellName("DateTime")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss")
    @CsvBindByName(column = "DateTime")
    private LocalDateTime localDateTime;

    @ExcelCellName("Duration")
    @CsvBindByName(column = "Duration")
    private Integer duration;
}
