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
                "jmix.liquibase.dropFirst=true"
        ).applyTo(applicationContext.getEnvironment())
    }
}
