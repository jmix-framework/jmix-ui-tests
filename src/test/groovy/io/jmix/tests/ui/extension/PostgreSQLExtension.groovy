package io.jmix.tests.ui.extension

import io.jmix.data.impl.liquibase.JmixLiquibase
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLExtension extends SpringExtension {

    private PostgreSQLContainer postgreSQLContainer

    @Override
    void beforeAll(ExtensionContext context) throws Exception {
        postgreSQLContainer = new PostgreSQLContainer()
                .withDatabaseName("postgres-test-db")
                .withUsername("test")
                .withPassword("pass")
        postgreSQLContainer.start()

        getApplicationContext(context).getBean(JmixLiquibase).afterPropertiesSet()
    }

    @Override
    void afterAll(ExtensionContext context) throws Exception {
        postgreSQLContainer.stop()
    }
}
