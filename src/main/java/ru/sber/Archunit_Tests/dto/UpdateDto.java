package ru.sber.Archunit_Tests.dto;

import java.util.List;
import java.util.UUID;

public record UpdateDto(
        UUID id,
        String name,
        Integer age,
        List<String> carsName
) {
}
