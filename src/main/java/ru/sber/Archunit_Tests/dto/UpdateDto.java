package ru.sber.Archunit_Tests.dto;

import java.util.List;

public record UpdateDto(
        String name,
        Integer age,
        List<String> carsName
) {
}
