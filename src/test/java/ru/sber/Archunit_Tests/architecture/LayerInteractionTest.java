package ru.sber.Archunit_Tests.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * Раздел 3: правила взаимодействия между слоями (проверки 13-18).
 *
 * <pre>
 * 13 Repository access   — репозитории вызываются только из сервисов
 * 14 Controller access   — контроллеры не обращаются к репозиториям напрямую
 * 15 Mapper access       — мапперы не обращаются к сервисам и репозиториям
 * 16 Entity isolation    — entity не зависят от сервисов, контроллеров и репозиториев
 * 17 DTO isolation       — DTO не зависят от сервисов и репозиториев
 * 18 Layer dependencies  — между слоями нет циклических зависимостей
 * </pre>
 *
 * Базовые правила именования и размещения классов лежат в {@link ArchitectureTest}.
 */
@AnalyzeClasses(packages = "ru.sber.Archunit_Tests", importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerInteractionTest {

    private static final String REPOSITORY_PACKAGE = "..repository..";
    private static final String SERVICE_PACKAGE = "..service..";
    private static final String CONTROLLER_PACKAGE = "..controller..";
    private static final String MAPPER_PACKAGE = "..mapper..";
    private static final String DTO_PACKAGE = "..dto..";

    @ArchTest
    static final ArchRule repositories_should_only_be_accessed_by_services =
            classes()
                    .that().resideInAPackage(REPOSITORY_PACKAGE)
                    .should().onlyHaveDependentClassesThat().resideInAnyPackage(SERVICE_PACKAGE);

    @ArchTest
    static final ArchRule controllers_should_not_access_repositories =
            noClasses()
                    .that().resideInAPackage(CONTROLLER_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(REPOSITORY_PACKAGE);

    @ArchTest
    static final ArchRule mappers_should_not_access_services_and_repositories =
            noClasses()
                    .that().resideInAPackage(MAPPER_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(SERVICE_PACKAGE, REPOSITORY_PACKAGE);

    @ArchTest
    static final ArchRule entities_should_not_depend_on_upper_layers =
            noClasses()
                    .that().areAnnotatedWith(Entity.class)
                    .should().dependOnClassesThat().resideInAnyPackage(SERVICE_PACKAGE, CONTROLLER_PACKAGE, REPOSITORY_PACKAGE);

    @ArchTest
    static final ArchRule dtos_should_not_depend_on_services_and_repositories =
            noClasses()
                    .that().resideInAPackage(DTO_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            SERVICE_PACKAGE,
                            REPOSITORY_PACKAGE);

    @ArchTest
    static final ArchRule layers_should_be_free_of_cycles =
            slices()
                    .matching("ru.sber.Archunit_Tests.(*)..")
                    .should().beFreeOfCycles();
}
