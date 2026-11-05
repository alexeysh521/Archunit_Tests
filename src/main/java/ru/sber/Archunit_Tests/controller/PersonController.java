package ru.sber.Archunit_Tests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sber.Archunit_Tests.dto.CreateDto;
import ru.sber.Archunit_Tests.dto.PersonDto;
import ru.sber.Archunit_Tests.dto.UpdateDto;
import ru.sber.Archunit_Tests.service.impl.PersonServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceImpl personService;

    @PostMapping("/create")
    public void createPerson(@RequestBody CreateDto createDto){
        personService.create(createDto);
    }

    @GetMapping("/getByID/{id}")
    public PersonDto getById(@PathVariable UUID id){
        return personService.get(id);
    }

    @PatchMapping("/update/{id}")
    public void updatePerson(@PathVariable UUID id,
                             @RequestBody UpdateDto dto){
        personService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){
        personService.delete(id);
    }
}

