package fr.herisson.missa;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("fr.herisson.missa");

        noClasses()
            .that()
                .resideInAnyPackage("fr.herisson.missa.service..")
            .or()
                .resideInAnyPackage("fr.herisson.missa.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..fr.herisson.missa.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
