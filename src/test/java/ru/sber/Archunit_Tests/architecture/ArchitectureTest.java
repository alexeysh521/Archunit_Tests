package ru.sber.Archunit_Tests.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "ru.sber.Archunit_Tests", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    @ArchTest
    static final ArchRule layers =
            layeredArchitecture().consideringOnlyDependenciesInLayers()
                    .layer("Controller").definedBy("..controller..")
                    .layer("Service").definedBy("..service..")
                    .layer("Repo").definedBy("..repository..")
                    .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                    .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                    .whereLayer("Repo").mayOnlyBeAccessedByLayers("Service");

    @ArchTest
    static final ArchRule services_naming =
            classes().that().resideInAPackage("..service.impl..")
                    .should().haveSimpleNameEndingWith("ServiceImpl")
                    .andShould().beAnnotatedWith(Service.class);

    @ArchTest
    static final ArchRule service_naming_interface =
            classes().that().resideInAPackage("..service")
                    .should().beInterfaces()
                    .andShould().haveSimpleNameEndingWith("Service");


    @ArchTest
    static final ArchRule service_do_not_touch_mappers =
            noClasses().that().resideInAPackage("..service..")
                    .should().dependOnClassesThat().haveSimpleNameEndingWith("Mapper");

}
