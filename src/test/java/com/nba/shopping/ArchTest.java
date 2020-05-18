package com.nba.shopping;

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
            .importPackages("com.nba.shopping");

        noClasses()
            .that()
                .resideInAnyPackage("com.nba.shopping.service..")
            .or()
                .resideInAnyPackage("com.nba.shopping.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.nba.shopping.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
