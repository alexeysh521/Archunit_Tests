package ru.sber.Archunit_Tests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sber.Archunit_Tests.dto.CreateDto;
import ru.sber.Archunit_Tests.dto.PersonDto;
import ru.sber.Archunit_Tests.dto.UpdateDto;
import ru.sber.Archunit_Tests.service.PersonService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private PersonService service;

    @PostMapping("/create")
    public void createPerson(@RequestBody CreateDto createDto){
        service.create(createDto);
    }

    @GetMapping("/getByID/{id}")
    public PersonDto getById(@PathVariable UUID id){
        return service.get(id);
    }

    @PatchMapping("/update/{id}")
    public void updatePerson(@PathVariable UUID id, @RequestBody UpdateDto dto){
        service.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }
}

