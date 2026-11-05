package ru.sber.Archunit_Tests.architecture;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import jakarta.persistence.Entity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
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
    static final ArchRule classes_should_reside_in_allowed_packages =
            classes()
                    .that().resideInAPackage("ru.sber.Archunit_Tests..")
                    .and().areNotAnnotatedWith(SpringBootApplication.class)
                    .should().resideInAnyPackage(
                            "..controller..",
                            "..service..",
                            "..repository..",
                            "..mapper..",
                            "..entity..",
                            "..dto..",
                            "..persistance.."
                    );

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


    // Правило "сервисы не должны зависеть от мапперов" удалено: слой мапперов введён
    // (PersonMapper), и сервис обязан его вызывать. Зависимости мапперов ограничены
    // в LayerInteractionTest (правило 15: маппер не ходит в сервисы и репозитории).

    @ArchTest
    static final ArchRule entities_should_end_with_entity =
            classes()
                    .that().areAnnotatedWith(Entity.class)
                    .should().haveSimpleNameEndingWith("Entity");

    @ArchTest
    static final ArchRule repositories_should_end_with_repository =
            classes()
                    .that().resideInAPackage("..repository..")
                    .and().areInterfaces()
                    .should().haveSimpleNameEndingWith("Repository");

    @ArchTest
    static final ArchRule controllers_should_end_with_controller =
            classes()
                    .that().areAnnotatedWith(RestController.class)
                    .or().areAnnotatedWith(Controller.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    public static final ArchRule all_public_service_methods_must_be_transactional =
            methods()
                    .that().areDeclaredInClassesThat().areAnnotatedWith(Service.class)
                    .and().arePublic()
                    .should().beAnnotatedWith(Transactional.class);

    @ArchTest
    public static final ArchRule read_methods_must_be_read_only =
            methods()
                    .that().areDeclaredInClassesThat().areAnnotatedWith(Service.class)
                    .and().arePublic()
                    .and().haveNameStartingWith("get")
                    .or().haveNameStartingWith("find")
                    .or().haveNameStartingWith("list")
                    .should().beAnnotatedWith(Transactional.class)
                    .andShould(new ArchCondition<JavaMethod>("be readOnly") {
                        @Override
                        public void check(JavaMethod method, ConditionEvents events) {
                            Transactional t = method.getAnnotationOfType(Transactional.class);
                            if (t != null && !t.readOnly()) {
                                events.add(SimpleConditionEvent.violated(method, method.getFullName() + " должен быть readOnly = true"));
                            }
                        }
                    });
}
