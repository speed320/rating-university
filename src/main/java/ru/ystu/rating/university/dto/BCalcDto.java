package ru.ystu.rating.university.dto;

public record BCalcDto(
        Integer year,
        Integer iteration,
        double b11,
        double b12,
        double b13,
        double b21,
        double sumB,
        String codeB11,
        String codeB12,
        String codeB13,
        String codeB21
) {
}

