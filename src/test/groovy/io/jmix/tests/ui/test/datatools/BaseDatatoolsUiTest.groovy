package io.jmix.tests.ui.test.datatools

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.OptionDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import io.jmix.tests.ui.test.utils.UiHelper

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static io.jmix.masquerade.Selectors.*

abstract class BaseDatatoolsUiTest extends BaseUiTest implements UiHelper {
    public static final String USER_ENTITY_NAME = "User"
    public static final String GAS_ENTITY_NAME = "Gas"
    public static final String CARRIER_ENTITY_NAME = "Carrier"
    public static final String SPACEPORT_ENTITY_NAME = "Space port"
    public static final String ATMOSPHERIC_GAS_ENTITY_NAME = "Atmospheric gas"
    public static final String ATMOSPHERE_ENTITY_NAME = "Atmosphere"
    public static final String DISCOUNTS_ENTITY_NAME = "Discounts"

    public static final String USER_FULL_STRING = "User (User)"
    public static final String GAS_FULL_STRING = "Gas (Gas)"
    public static final String CARRIER_FULL_STRING = "Carrier (Carrier)"
    public static final String SPACEPORT_FULL_STRING = "Space port (SpacePort)"
    public static final String ATMOSPHERIC_GAS_FULL_STRING = "Atmospheric gas (AtmosphericGas)"
    public static final String ATMOSPHERE_FULL_STRING = "Atmosphere (Atmosphere)"
    public static final String DISCOUNTS_FULL_STRING = "Discounts (Discounts)"

    public static final String NON_REMOVED_ONLY_MODE = "Show non-removed records only"
    public static final String REMOVED_ONLY_MODE = "Show removed records only"
    public static final String ALL_MODE = "Show all records"

    public static final String ADMIN_USERNAME = "admin"
    public static final String USERNAME1 = "myUser"
    public static final String USERNAME2 = "secondUser"
    public static final String USERNAME3 = "thirdUser"
    public static final String USERNAME4 = "forthUser"
    public static final String USERNAME5 = "fifthUser"
    public static final String USERNAME6 = "sixthUser"

    public static final String GAS_TABLE_JTEST_ID = "GasTable_composition"
    public static final String CARRIER_TABLE_JTEST_ID = "CarrierTable"
    public static final String SPACEPORT_TABLE_JTEST_ID = "SpacePortTable"
    public static final String ATMOSPHERIC_GAS_TABLE_JTEST_ID = "AtmosphericGasTable_composition"
    public static final String ATMOSPHERE_TABLE_JTEST_ID = "AtmosphereTable_composition"
    public static final String USER_TABLE_JTEST_ID = "UserTable_composition"
    public static final String USER_BROWSER_TABLE_JTEST_ID = "usersTable_composition"
    public static final String DISCOUNTS_TABLE_JTEST_ID = "DiscountsTable_composition"

    public static final String ALERT_NOTIFICATION_CAPTION = "Alert"

    public static final String NON_DECIMAL_VALUE = "qwerty"
    public static final String FIRST_DECIMAL_VALUE = "10.5"
    public static final String SECOND_DECIMAL_VALUE = "20.5"

    public static final String OXYGEN_GAS_NAME = "Oxygen"
    public static final String HELIUM_GAS_NAME = "Helium"
    public static final String TEST_GAS_NAME = "Test gas"
    public static final String TEST_GAS_1_NAME = "Test gas 1"

    public static final String CARRIER_FIRST_NAME = "carrier1"
    public static final String CARRIER_TEST_NAME = "Test carrier"
    public static final String SPACEPORT_FIRST_NAME = "spaceport1"
    public static final String SPACEPORT_SECOND_NAME = "spaceport2"
    public static final String SPACEPORT_TEST_NAME = "Test spaceport"

    public static final String PRESSURE = "pressure"
    public static final String DESCRIPTION = "description"
    public static final String NAME = "name"
    public static final String LATITUDE = "latitude"
    public static final String LONGITUDE = "longitude"

    /**
     * Opens Inspector Window
     */
    static void openInspectorWindow(int index) {
        ElementsCollection elementsCollection = $$(byChain(byJTestId('jmixContextMenu'),
                byClassName('jmix-cm-button')))

        elementsCollection.get(index).click()
    }

    /**
     * Closes Inspector Window
     */
    static void closeInspectorWindow() {
        SelenideElement closeBtn = $(byClassName("v-window-closebox"))
        closeBtn.click()
    }

    /**
     * Exports entity from Inspector Window
     * @param formatJTestId - j-test-id of button
     * @param tableJTestId - j-test-id of table
     */
    static void exportFromInspectorWindow(String formatJTestId, String tableJTestId) {
        SelenideElement exportBtn = $(byChain(byJTestId(tableJTestId), byClassName("v-popupbutton")))
        exportBtn.click()
        SelenideElement formatBtn = $(byJTestId(formatJTestId))
        formatBtn.click()

    }

    /**
     * Removes created instances with Soft Delete trait
     */
    static void wipeOutData(String entityName, String entityFullName, String showMode, String tableJTestId, String name) {
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(entityName, entityFullName)
            selectShowMode(showMode)

            selectRowInTableByText(name, tableJTestId)
            clickButton(wipeOut)
            $j(OptionDialog).confirm()
        }
    }

    /**
     * Removes created instances without Soft Delete trait
     */
    static void cleanData(String entityName, String entityFullName, String showMode, String tableJTestId, String name) {
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(entityName, entityFullName)
            selectShowMode(showMode)

            selectRowInTableByText(name, tableJTestId)
            clickButton(remove)
            $j(ConfirmationDialog).confirmChanges()
        }
    }

    /**
     * Opens entity creating screen from Entity inspector browser
     * @param entityname
     * @param entityFullString - full entity name string
     */
    static void openEntityCreatingScreen(String entityName, String entityFullString) {
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(entityName, entityFullString)
            clickButton(create)
        }
    }
}
