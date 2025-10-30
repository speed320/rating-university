package ru.ystu.rating.university.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// импорт/экспорт
public record BParamsBundle(
        @JsonProperty("class")String clazz,                   // "B"
        List<BParamsDto> data
) {}

