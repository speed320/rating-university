package ru.ystu.rating.university.dto;

// что отдаем после расчёта
public record BCalcDto(
        Integer year,
        Integer iteration,
        double B11,
        double B12,
        double B13,
        double B21,
        double totalB,
        String calculatedAt
) {}

