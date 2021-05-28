package io.jmix.tests.ui.test.datatools.entity

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.browsers.GasBrowse
import io.jmix.tests.ui.screen.administration.datatools.editors.AtmosphericGasEditor
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
class EntityCompositionOtoOUiTest extends BaseDatatoolsUiTest {

    @Test
    @DisplayName("Creates entity with Composition One-to-One relationship from Entity Inspector Browser")
    void createEntity() {
        loginAsAdmin()

        openEntityCreatingScreen(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING)

        $j(AtmosphericGasEditor).with {
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            fillTextField(volume, NON_DECIMAL_VALUE)

            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            fillTextField(volume, FIRST_DECIMAL_VALUE)
            openGasesLookup()

        }

        $j(GasBrowse).with {
            selectRowInTableByText(TEST_GAS_1_NAME, GAS_TABLE_JTEST_ID)
            clickButton(ok)
        }

        $j(AtmosphericGasEditor).with {
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(TEST_GAS_1_NAME, ATMOSPHERIC_GAS_TABLE_JTEST_ID)

        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, TEST_GAS_1_NAME)
    }

    @Test
    @DisplayName("Edits entity with Composition One-to-One relationship from Entity Inspector Browser")
    void editEntity() {
        loginAsAdmin()

        openEntityCreatingScreen(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING)

        $j(AtmosphericGasEditor).with {
            fillTextField(volume, FIRST_DECIMAL_VALUE)
            openGasesLookup()
        }

        $j(GasBrowse).with {
            selectRowInTableByText(TEST_GAS_1_NAME, GAS_TABLE_JTEST_ID)
            clickButton(ok)
        }

        $j(AtmosphericGasEditor).with {
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).with {
            checkRecordIsDisplayed(TEST_GAS_1_NAME, ATMOSPHERIC_GAS_TABLE_JTEST_ID)
            selectRowInTableByText(TEST_GAS_1_NAME, ATMOSPHERIC_GAS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(AtmosphericGasEditor).with {
            fillTextField(volume, SECOND_DECIMAL_VALUE)
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(SECOND_DECIMAL_VALUE, ATMOSPHERIC_GAS_TABLE_JTEST_ID)

        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, TEST_GAS_1_NAME)
    }
}
