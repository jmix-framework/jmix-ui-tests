package io.jmix.tests.ui.initializer

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class TestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "main.datasource.jdbcUrl=jdbc:tc:postgresql:9.6.12:///postgres-test-db",
                "main.datasource.username=test",
                "main.datasource.password=pass",
                "jmix.data.dbmsType=postgres",
                "main.liquibase.change-log=io/jmix/tests/liquibase/changelog.xml",
                "main.liquibase.drop-first=true"
        ).applyTo(applicationContext.getEnvironment())
    }
}
