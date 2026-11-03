package ru.sber.Archunit_Tests.dto;

import java.util.List;

public record CreateDto(
        String name,
        Integer age,
        List<String> carsName
) {
}
