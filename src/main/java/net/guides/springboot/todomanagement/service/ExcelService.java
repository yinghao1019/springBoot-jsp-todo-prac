package net.guides.springboot.todomanagement.service;

import lombok.RequiredArgsConstructor;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.utils.LoginUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    public List<Todo> readExcel(MultipartFile file) throws IOException {
        List<Todo> todos = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        String userName = LoginUtils.getLoggedInUserName();
        for (Row row : sheet) {
            if (row.getLastCellNum() == 0) continue;
            String description = getCellValue(row.getCell(0));
            Date targetDate = getDateCell(row.getCell(1));
            // TODO null處理
            Todo todo = new Todo();
            todo.setDescription(description);
            todo.setTargetDate(targetDate);
            todo.setUserName(userName);
            todos.add(todo);
        }
        return todos;
    }

    private Date getDateCell(Cell cell) {
        if (cell != null
                && cell.getCellType() == CellType.NUMERIC
                && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue();
        }
        return null;
    }

    private String getCellValue(Cell cell) {

        String value;
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                value = cell.getRichStringCellValue().getString();
        }
        return StringUtils.strip(value);
    }

    private boolean cellIsStrikeOut(Cell cell) {
        if (cell == null) {
            return false;
        }
        XSSFCell xssfCell = (XSSFCell) cell;
        return xssfCell.getCellStyle().getFont().getStrikeout();
    }
}
