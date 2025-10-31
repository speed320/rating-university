package ru.ystu.rating.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.ystu.rating.university.dto.BCalcDto;
import ru.ystu.rating.university.dto.BParamsBundle;
import ru.ystu.rating.university.dto.BParamsDto;
import ru.ystu.rating.university.service.BService;

import java.util.List;

@RestController
@CrossOrigin(origins="https://rating-university-web.onrender.com")
@RequestMapping("/api/b")
@Tag(name = "Class B", description = "Параметры и расчёты класса B")
public class BController {

    private final BService service;

    public BController(BService service) {
        this.service = service;
    }

    // Параметры — за год
    @GetMapping("/params/{year}")
    @Operation(summary = "Получить все введенные параметры за год (все итерации)")
    public List<BParamsDto> getParamsByYear(@PathVariable int year) {
        return service.getParamsByYear(year);
    }

    // Параметры — за все годы
    @GetMapping("/params")
    @Operation(summary = "Получить все введенные параметры за все года")
    public List<BParamsDto> getParamsAll() {
        return service.getParamsAll();
    }

    // Импорт
    @PostMapping("/import")
    @Operation(summary = "Импортировать параметры из JSON", description = "Структура: {\"class\":\"B\",\"data\":[ ... ]}")
    public void importParams(@RequestBody BParamsBundle bundle) {
        service.importBundle(bundle);
    }

    // Экспорт
    @GetMapping("/export")
    @Operation(summary = "Экспортировать все параметры в JSON")
    public BParamsBundle exportParams() {
        return service.exportAll();
    }

    // Пересчёт по всем годам
    @PostMapping("/calc")
    @Operation(summary = "Пересчитать значения по всем годам и сохранить")
    public List<BCalcDto> computeAll() {
        return service.computeAllYears();
    }

    // Получить рассчитанные значения за год
    @GetMapping("/calc/{year}")
    @Operation(summary = "Получить рассчитанные значения за год (последние сохранённые)")
    public List<BCalcDto> getCalcByYear(@PathVariable int year) {
        return service.getCalcByYear(year);
    }

    // Получить рассчитанные значения за все годы
    @GetMapping("/calc")
    @Operation(summary = "Получить рассчитанные значения за все годы (последние сохранённые)")
    public List<BCalcDto> getCalcAll() {
        return service.getCalcAll();
    }
}

