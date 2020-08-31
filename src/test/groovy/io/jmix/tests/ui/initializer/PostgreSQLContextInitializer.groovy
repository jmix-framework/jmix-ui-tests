package io.jmix.tests.ui.initializer


import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static PostgreSQLContainer postgreSQL = new PostgreSQLContainer()
            .withDatabaseName("postgres-test-db")
            .withUsername("test")
            .withPassword("pass")

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        postgreSQL.start()
        TestPropertyValues.of(
                "main.datasource.jdbcUrl=" + postgreSQL.getJdbcUrl(),
                "main.datasource.username=" + postgreSQL.getUsername(),
                "main.datasource.password=" + postgreSQL.getPassword(),
                "jmix.data.dbmsType=postgres"
        ).applyTo(applicationContext.getEnvironment())
    }
}
