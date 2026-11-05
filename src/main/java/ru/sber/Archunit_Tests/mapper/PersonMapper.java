package ru.sber.Archunit_Tests.mapper;

import org.springframework.stereotype.Component;
import ru.sber.Archunit_Tests.dto.PersonDto;
import ru.sber.Archunit_Tests.persistance.model.PersonEntity;

import java.util.List;

@Component
public class PersonMapper {

    public PersonDto toDto(PersonEntity entity) {
        return new PersonDto(
                entity.getId(),
                entity.getName(),
                entity.getAge(),
                entity.getCarsName() == null ? List.of() : List.copyOf(entity.getCarsName())
        );
    }
}
