package io.jmix.tests.ui.test.datatools.entitycreating

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.browsers.AtmosphericGasBrowse
import io.jmix.tests.ui.screen.administration.datatools.browsers.GasBrowse
import io.jmix.tests.ui.screen.administration.datatools.editors.AtmosphereEditor
import io.jmix.tests.ui.screen.administration.datatools.editors.AtmosphericGasEditor
import io.jmix.tests.ui.screen.administration.datatools.editors.GasEditor
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static io.jmix.masquerade.Selectors.$j

class DatatoolsEntityCompOtoMUiTests extends BaseDatatoolsUiTests {

    static void fillAndSaveGas(String gas) {
        $j(GasEditor).with {
            fillTextField(name, gas)
            clickButton(ok)
        }
    }

    static void generateGases(String gas1, String gas2) {
        openEntityCreatingScreen(GAS_ENTITY_NAME, GAS_FULL_STRING)
        fillAndSaveGas(gas1)
        $j(EntityInspectorBrowse).with {
            clickButton(create)
        }
        fillAndSaveGas(gas2)
    }

    static void createAtmosphericGas(String gasName, String gasVolume) {
        $j(AtmosphericGasBrowse).with {
            clickButton($j(Button, "create"))
        }

        $j(AtmosphericGasEditor).with {
            fillTextField(volume, gasVolume)
            openGasesLookup()
        }

        $j(GasBrowse).with {
            selectRowInTableByText(gasName, GAS_TABLE_JTEST_ID)
            clickButton(ok)
        }

        $j(AtmosphericGasEditor).with {
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Create entity with Composition One-to-Many relationship from Entity Inspector Browser")
    void createEntity() {
        loginAsAdmin()

        generateGases(OXYGEN_GAS_NAME, HELIUM_GAS_NAME)

        openEntityCreatingScreen(ATMOSPHERE_ENTITY_NAME, ATMOSPHERE_FULL_STRING)

        $j(AtmosphereEditor).with {
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            fillTextField($j(TextField, PRESSURE), NON_DECIMAL_VALUE)
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            fillTextField($j(TextField, PRESSURE), FIRST_DECIMAL_VALUE)
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            fillTextField($j(TextField, DESCRIPTION), NON_DECIMAL_VALUE)

            openGasesTab()
        }
        createAtmosphericGas(OXYGEN_GAS_NAME, FIRST_DECIMAL_VALUE)
        createAtmosphericGas(HELIUM_GAS_NAME, SECOND_DECIMAL_VALUE)

        $j(AtmosphereEditor).with {
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(FIRST_DECIMAL_VALUE, ATMOSPHERE_TABLE_JTEST_ID)

        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, HELIUM_GAS_NAME)
        cleanData(ATMOSPHERE_ENTITY_NAME, ATMOSPHERE_FULL_STRING, ALL_MODE, ATMOSPHERE_TABLE_JTEST_ID, FIRST_DECIMAL_VALUE)
        cleanData(GAS_ENTITY_NAME, GAS_FULL_STRING, ALL_MODE, GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
        cleanData(GAS_ENTITY_NAME, GAS_FULL_STRING, ALL_MODE, GAS_TABLE_JTEST_ID, HELIUM_GAS_NAME)

    }

    @Test
    @DisplayName("Edit entity with Composition One-to-Many relationship from Entity Inspector Browser")
    void editEntity() {
        loginAsAdmin()

        generateGases(OXYGEN_GAS_NAME, HELIUM_GAS_NAME)

        openEntityCreatingScreen(ATMOSPHERE_ENTITY_NAME, ATMOSPHERE_FULL_STRING)

        $j(AtmosphereEditor).with {
            fillTextField($j(TextField, PRESSURE), FIRST_DECIMAL_VALUE)
            fillTextField($j(TextField, DESCRIPTION), NON_DECIMAL_VALUE)
            openGasesTab()
        }
        createAtmosphericGas(OXYGEN_GAS_NAME, FIRST_DECIMAL_VALUE)
        createAtmosphericGas(HELIUM_GAS_NAME, SECOND_DECIMAL_VALUE)

        $j(AtmosphereEditor).with {
            openGeneralTab()
            fillTextField($j(TextField, PRESSURE), SECOND_DECIMAL_VALUE)
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(SECOND_DECIMAL_VALUE, ATMOSPHERE_TABLE_JTEST_ID)

        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
        cleanData(ATMOSPHERIC_GAS_ENTITY_NAME, ATMOSPHERIC_GAS_FULL_STRING, ALL_MODE, ATMOSPHERIC_GAS_TABLE_JTEST_ID, HELIUM_GAS_NAME)
        cleanData(ATMOSPHERE_ENTITY_NAME, ATMOSPHERE_FULL_STRING, ALL_MODE, ATMOSPHERE_TABLE_JTEST_ID, SECOND_DECIMAL_VALUE)
        cleanData(GAS_ENTITY_NAME, GAS_FULL_STRING, ALL_MODE, GAS_TABLE_JTEST_ID, OXYGEN_GAS_NAME)
        cleanData(GAS_ENTITY_NAME, GAS_FULL_STRING, ALL_MODE, GAS_TABLE_JTEST_ID, HELIUM_GAS_NAME)

    }

}
