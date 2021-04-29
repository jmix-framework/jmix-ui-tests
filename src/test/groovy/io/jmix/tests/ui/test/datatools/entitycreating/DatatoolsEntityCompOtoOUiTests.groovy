package io.jmix.tests.ui.test.datatools.entitycreating


import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.browsers.GasBrowse
import io.jmix.tests.ui.screen.administration.datatools.editors.AtmosphericGasEditor
import io.jmix.tests.ui.screen.administration.datatools.editors.GasEditor
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static io.jmix.masquerade.Selectors.$j

class DatatoolsEntityCompOtoOUiTests extends BaseDatatoolsUiTests {

    static void fillAndSaveGas(String gas) {
        $j(GasEditor).with {
            fillTextField(name, gas)
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Create entity with Composition One-to-One relationship from Entity Inspector Browser")
    void createEntity() {
        loginAsAdmin()

        openEntityCreatingScreen(GAS_ENTITY_NAME, GAS_FULL_STRING)
        fillAndSaveGas(OXYGEN_GAS_NAME)

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
            selectRowInTableByText(OXYGEN_GAS_NAME, GAS_TABLE_JTEST_ID)
            clickButton(ok)
        }

        $j(AtmosphericGasEditor).with {
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(OXYGEN_GAS_NAME, ATMOSPHERIC_GAS_TABLE_JTEST_ID)

        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
        cleanData(GAS_ENTITY_NAME, GAS_FULL_STRING, ALL_MODE, GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
    }

    @Test
    @DisplayName("Edit entity with Composition One-to-One relationship from Entity Inspector Browser")
    void editEntity() {
        loginAsAdmin()

        openEntityCreatingScreen(GAS_ENTITY_NAME, GAS_FULL_STRING)
        fillAndSaveGas(OXYGEN_GAS_NAME)

        openEntityCreatingScreen(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING)

        $j(AtmosphericGasEditor).with {
            fillTextField(volume, FIRST_DECIMAL_VALUE)
            openGasesLookup()
        }

        $j(GasBrowse).with {
            selectRowInTableByText(OXYGEN_GAS_NAME, GAS_TABLE_JTEST_ID)
            clickButton(ok)
        }

        $j(AtmosphericGasEditor).with {
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).with {
            checkRecordIsDisplayed(OXYGEN_GAS_NAME, ATMOSPHERIC_GAS_TABLE_JTEST_ID)
            selectRowInTableByText(OXYGEN_GAS_NAME, ATMOSPHERIC_GAS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(AtmosphericGasEditor).with {
            fillTextField(volume, SECOND_DECIMAL_VALUE)
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(SECOND_DECIMAL_VALUE, ATMOSPHERIC_GAS_TABLE_JTEST_ID)

        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
        cleanData(GAS_ENTITY_NAME, GAS_FULL_STRING, ALL_MODE, GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
    }
}
