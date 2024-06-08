package com.icsd.healthcare.shared.utils;

import com.icsd.healthcare.slot.SlotCsvRepresentation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class ExcelParser {

    public <T> Set<T> parseExcel(MultipartFile file, Function<Row, T> mapper) throws IOException {
        Set<T> data = new HashSet<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            sheet.forEach(row -> {
                if (row.getRowNum() != 0) {
                    T object = mapper.apply(row);
                    data.add(object);
                }
            });
        }

        return data;
    }


}