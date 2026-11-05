package ru.sber.Archunit_Tests.dto;

import ru.sber.Archunit_Tests.persistance.model.PersonEntity;

import java.util.List;
import java.util.UUID;

public record PersonDto(
        UUID id,
        String name,
        Integer age,
        List<String> carsName
) {
    public static PersonDto convertTo(PersonEntity e) {
        return new PersonDto(e.getId(), e.getName(), e.getAge(), e.getCarsName() != null
                ? List.copyOf(e.getCarsName()) : null);
    }
}
