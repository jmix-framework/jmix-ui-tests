package io.jmix.tests.ui.test.datatools.entitycreating

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.editors.GasEditor
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,datatools'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class DatatoolsSimpleEntityUiTests extends BaseDatatoolsUiTest {

    static void fillAndSaveGas(String gas) {
        $j(GasEditor).with {
            fillTextField(name, gas)
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Creates simple entity from Entity Inspector Browser")
    void createEntity() {
        loginAsAdmin()

        openEntityCreatingScreen(GAS_ENTITY_NAME, GAS_FULL_STRING)

        $j(GasEditor).with {
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)
            fillTextField(name, OXYGEN_GAS_NAME)
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(OXYGEN_GAS_NAME, GAS_TABLE_JTEST_ID)

        cleanData(GAS_ENTITY_NAME, GAS_FULL_STRING, ALL_MODE, GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
    }

    @Test
    @DisplayName("Edits simple entity from Entity Inspector Browser")
    void editEntity() {
        loginAsAdmin()

        $j(EntityInspectorBrowse).with {
            checkRecordIsDisplayed(TEST_GAS_NAME, GAS_TABLE_JTEST_ID)
            selectRowInTableByText(TEST_GAS_NAME, GAS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        fillAndSaveGas(HELIUM_GAS_NAME)

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(HELIUM_GAS_NAME, GAS_TABLE_JTEST_ID)
    }
}