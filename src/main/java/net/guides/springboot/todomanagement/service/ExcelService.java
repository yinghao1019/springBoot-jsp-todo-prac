package net.guides.springboot.todomanagement.service;

import lombok.RequiredArgsConstructor;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.repository.TodoRepository;
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

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final TodoRepository todoRepository;

    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Todo> todos =
                todoRepository.findByUserNameOrderByTargetDateDesc(
                        LoginUtils.getLoggedInUserName());
        // 建立 Excel 檔
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("資料表");

        // 標題列
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("描述");
        header.createCell(1).setCellValue("日期");

        int rowNum = 1;
        for (Todo todo : todos) {
            Row row = sheet.createRow(rowNum);
            row.createCell(0, CellType.STRING).setCellValue(todo.getDescription());
            row.createCell(1).setCellValue(todo.getTargetDate());
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }

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
