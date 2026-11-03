package ru.sber.Archunit_Tests.service.impl;

import ru.sber.Archunit_Tests.dto.CreateDto;
import ru.sber.Archunit_Tests.dto.PersonDto;
import ru.sber.Archunit_Tests.dto.UpdateDto;

import java.util.UUID;

public interface PersonServiceImpl {
    void create(CreateDto dto);
    PersonDto get(UUID id);
    void update(UpdateDto dto);
    void delete(UUID id);
}
