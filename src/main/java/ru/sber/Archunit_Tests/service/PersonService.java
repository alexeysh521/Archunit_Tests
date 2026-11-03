package ru.sber.Archunit_Tests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sber.Archunit_Tests.persistance.model.PersonEntity;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void create(CreateDto dto){
        PersonEntity person = PersonEntity.builder()
                .name(dto.name)
                .age(dto.age)
                .carsName(dto.carsName)
                .build();

        personRepository.save(person);
    }

    public PersonDto get(UUID id){
        return personRepository.findById(id); // через маппер привести к PersonDto
    }

    public void update(UpdateDto dto){
        PersonEntity person = personRepository.findById(dto.id);
        if(dto.name != null) person.setName(dto.name);
        if(dto.age != null) person.setAge(dto.age);
        if(dto.carsName != null) person.setCarsName(dto.carsName);
        personRepository.save(person);
    }

    public void delete(UUID id){
        personRepository.deleteById(id);
    }

    // проверки на существование и тд пока опускаем, задача - сделать тестирование
}
