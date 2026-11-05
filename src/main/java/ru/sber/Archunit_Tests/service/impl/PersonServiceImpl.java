package ru.sber.Archunit_Tests.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.Archunit_Tests.dto.CreateDto;
import ru.sber.Archunit_Tests.dto.PersonDto;
import ru.sber.Archunit_Tests.dto.UpdateDto;
import ru.sber.Archunit_Tests.mapper.PersonMapper;
import ru.sber.Archunit_Tests.persistance.model.PersonEntity;
import ru.sber.Archunit_Tests.persistance.repository.PersonRepository;
import ru.sber.Archunit_Tests.service.PersonService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public void create(CreateDto dto) {
        PersonEntity person = PersonEntity.builder()
                .name(dto.name())
                .age(dto.age())
                .carsName(dto.carsName())
                .build();

        personRepository.save(person);
    }

    public PersonDto get(UUID id) {
        return personRepository.findById(id).map(personMapper::toDto).orElseThrow(
                () -> new EntityNotFoundException("Сущность не найдена"));
    }

    public void update(UUID id, UpdateDto dto){
        PersonEntity person = personRepository.findById(id).orElseThrow();
        if(dto.name() != null) person.setName(dto.name());
        if(dto.age() != null) person.setAge(dto.age());
        if(dto.carsName() != null) person.setCarsName(dto.carsName());
        personRepository.save(person);
    }

    public void delete(UUID id) {
        personRepository.deleteById(id);
    }

    // проверки на существование и тд пока опускаем, задача - сделать тестирование
}
