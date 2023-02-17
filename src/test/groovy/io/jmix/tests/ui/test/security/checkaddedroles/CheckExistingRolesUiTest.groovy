package io.jmix.tests.ui.test.security.checkaddedroles

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.security.browser.RoleBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.security.BaseSecurityUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,security'])
@ContextConfiguration(initializers = TestContextInitializer)
class CheckExistingRolesUiTest extends BaseSecurityUiTest {

    @Test
    @DisplayName("Checks the list of existing roles")
    void checkExistingRolesList() {
        loginAsAdmin()
        $j(MainScreen).with {
            openResourceRoleBrowse()
        }
        $j(RoleBrowse).with {
            checkRecordIsDisplayed("Data Tools: Entity Information window", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("Dynamic Attributes: administration", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("Dynamic Attributes: read-only access", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("Entity log: configure and view entity logs", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("Full Access", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("Reports: full access", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("Reports: run reports", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("Reports: run reports using REST API", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("UI: edit filters", ROLE_MODELS_TABLE_JTEST_ID)
            checkRecordIsDisplayed("UI: minimal access", ROLE_MODELS_TABLE_JTEST_ID)
        }
    }
}
