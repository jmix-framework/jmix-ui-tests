package io.jmix.tests.ui.test.datatools.entity

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.browsers.CarriersBrowse
import io.jmix.tests.ui.screen.administration.datatools.editors.CarrierEditor
import io.jmix.tests.ui.screen.administration.datatools.editors.SpaceportEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,datatools'])
class EntityAssociationMtoMUiTest extends BaseDatatoolsUiTest {

    void fillSpacePortFields(String name, String latitude, String longitude) {

        fillTextField($j(TextField, NAME), name)
        fillTextField($j(TextField, LATITUDE), latitude)
        fillTextField($j(TextField, LONGITUDE), longitude)
    }

    void createCarrier() {
        openEntityCreatingScreen(CARRIER_ENTITY_NAME, CARRIER_FULL_STRING)

        $j(CarrierEditor).with {
            fillTextField($j(TextField, NAME), CARRIER_FIRST_NAME)
            clickButton(ok)
        }
    }

    void createSpaceport() {
        openEntityCreatingScreen(SPACEPORT_ENTITY_NAME, SPACEPORT_FULL_STRING)

        $j(SpaceportEditor).with {
            fillSpacePortFields(SPACEPORT_FIRST_NAME, FIRST_DECIMAL_VALUE, SECOND_DECIMAL_VALUE)
            openCarriersTab()
            clickButton($j(Button, "add"))
        }

        $j(CarriersBrowse).with {
            selectRowInTableByText(CARRIER_FIRST_NAME, CARRIER_TABLE_JTEST_ID)
            clickButton(ok)
        }

        $j(SpaceportEditor).with {
            checkRecordIsDisplayed(CARRIER_FIRST_NAME, CARRIER_TABLE_JTEST_ID)
            clickButton(ok)
        }
    }

    void checkAddedSpaceport(String spaceportName, String carrierName) {
        $j(EntityInspectorBrowse).with {
            findEntityByFilter(CARRIER_ENTITY_NAME, CARRIER_FULL_STRING)
            selectRowInTableByText(carrierName, CARRIER_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(CarrierEditor).with {
            openSpacePortsTab()
            checkRecordIsDisplayed(spaceportName, SPACEPORT_TABLE_JTEST_ID)
            clickButton(ok)
        }
    }

    void renameSpaceport(String oldName, String newName){
        $j(EntityInspectorBrowse).with {
            findEntityByFilter(SPACEPORT_ENTITY_NAME, SPACEPORT_FULL_STRING)
            selectRowInTableByText(oldName, SPACEPORT_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(SpaceportEditor).with {
            fillTextField(name, newName)
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Creates entity with Association Many-to-Many relationship from Entity Inspector Browser")
    void createEntity() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        createCarrier()
        createSpaceport()
        checkAddedSpaceport(SPACEPORT_FIRST_NAME, CARRIER_FIRST_NAME)

        cleanData(CARRIER_ENTITY_NAME, CARRIER_FULL_STRING, ALL_MODE, CARRIER_TABLE_JTEST_ID, CARRIER_FIRST_NAME)
        cleanData(SPACEPORT_ENTITY_NAME, SPACEPORT_FULL_STRING, ALL_MODE, SPACEPORT_TABLE_JTEST_ID, SPACEPORT_FIRST_NAME)
    }

    @Test
    @DisplayName("Edits entity with Association Many-to-Many relationship from Entity Inspector Browser")
    void editEntity() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        renameSpaceport(SPACEPORT_TEST_NAME,SPACEPORT_SECOND_NAME)

        checkAddedSpaceport(SPACEPORT_SECOND_NAME, CARRIER_TEST_NAME)

        renameSpaceport(SPACEPORT_SECOND_NAME,SPACEPORT_TEST_NAME)

    }
}
