package ru.sber.Archunit_Tests.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.Archunit_Tests.persistance.model.PersonEntity;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
}
