package net.guides.springboot.todomanagement.controller;

import lombok.RequiredArgsConstructor;

import net.guides.springboot.todomanagement.model.Todo;
import net.guides.springboot.todomanagement.service.ExcelService;
import net.guides.springboot.todomanagement.service.TodoService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/excel")
@RequiredArgsConstructor
public class FileController {
    private final ExcelService excelService;
    private final TodoService todoService;

    @PostMapping(value = "/import")
    public String Page(@RequestParam("file") MultipartFile file) throws IOException {
        List<Todo> excelTodos = excelService.readExcel(file);
        todoService.saveTodoList(excelTodos);
        return "redirect:/list-todos";
    }
}
