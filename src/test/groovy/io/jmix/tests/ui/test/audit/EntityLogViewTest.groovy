package io.jmix.tests.ui.test.audit

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.audit.EntityLogBrowse
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.individual.IndividualBrowse
import io.jmix.tests.ui.screen.application.individual.IndividualEditor
import io.jmix.tests.ui.screen.system.dialog.OptionDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Selectors.byClassName
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText
import static io.jmix.tests.ui.menu.Menus.*
import static org.openqa.selenium.Keys.ENTER

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,entity-log'])
@ContextConfiguration(initializers = TestContextInitializer)
class EntityLogViewTest extends BaseUiTest {

    public static final String BRONZE = "Bronze"
    public static final String IVANOV = "Ivanov"
    public static final String IVAN = "Ivan"
    public static final String CREATE = "Create"
    public static final String INDIVIDUAL = 'Individual'
    public static final String MODIFY = "Modify"
    public static final String PETROV = "Petrov"
    public static final String PETR = "Petr"
    public static final String NEW_EMAIL = "modified@test.com"
    public static final String NEW_PETROV = "New_Petrov"
    public static final String FEDOROV = "Fedorov"
    public static final String DELETE = "Delete"
    public static final String FEDOR = "Fedor"
    public static final String SHOW_REMOVED_RECORDS = "Show removed records only"
    public static final String RESTORE = "Restore"

    @Test
    @DisplayName("Checks created setup record")
    void checkCreatedRecord() {
        loginAsAdmin()
        $j(MainScreen).with {
            sideMenu.openItem(INDIVIDUAL_BROWSE)
        }
        $j(IndividualBrowse).with {
            create.shouldBe(ENABLED)
                    .click()
            $j(IndividualEditor).with {
                grade.shouldBe(EDITABLE)
                        .openOptionsPopup()
                        .select(BRONZE)
                        .shouldHave(value(BRONZE))
                firstName.shouldBe(EDITABLE)
                        .setValue(IVAN)
                lastName.shouldBe(EDITABLE)
                        .setValue(IVANOV)
                saveChanges()
            }
            checkEntitySaved(IVAN, IVANOV)
        }
        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
        }
        $j(EntityLogBrowse).with {
            clickClear()
            setEntity(INDIVIDUAL)
            clickSearch()
            checkIndividualRecord()

            //check valid change type value
            setChangeType(CREATE)
            clickSearch()
            selectLoggedEntity(CREATE, INDIVIDUAL)

            //check inappropriate change type value
            setChangeType(MODIFY)
            clickSearch()
            checkRecordDoesNotDisplay(CREATE, IVAN)

            //check instance lookup
            setInstance(IVAN)
            setChangeType(CREATE)
            clickSearch()
            checkIndividualRecord()

            //check clear button
            clickClear()
            checkIndividualRecord()

            //check date fields
            setEntity(INDIVIDUAL)
            fromDateField.shouldBe(VISIBLE)
                    .setDateValue("01012000")
            tillDateField.shouldBe(VISIBLE)
                    .setDateValue("02012000")
            clickSearch()
            checkRecordDoesNotDisplay(CREATE, IVAN)

            //check user created entity
            clickClear()
            setUserValue("admin")
            setEntity(INDIVIDUAL)
            clickSearch()
            checkIndividualRecord()
        }

        //check inappropriate user
        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
        }
        $j(EntityLogBrowse).with {
            setUserValue("audit")
            clickSearch()
            checkRecordDoesNotDisplay(CREATE, IVAN)
        }
    }

    @Test
    @DisplayName("Checks modified record for changes not logged setup")
    void checkModifiedRecordNotSetup() {
        loginAsAdmin()
        $j(MainScreen).with {
            sideMenu.openItem(INDIVIDUAL_BROWSE)
        }
        $j(IndividualBrowse).with {
            editIndividual(PETR)
            $j(IndividualEditor).with {
                email.shouldBe(EDITABLE)
                        .setValue(NEW_EMAIL)
                        .shouldHave(value(NEW_EMAIL))
                saveChanges()
            }
            checkEntitySaved(PETR, NEW_EMAIL)
        }
        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
        }
        $j(EntityLogBrowse).with {
            setEntity(INDIVIDUAL)
            setChangeType(MODIFY)
            clickSearch()
            checkRecordDoesNotDisplay(CREATE, PETR)
        }
    }

    @Test
    @DisplayName("Checks modified record for changes with logged setup")
    void checkModifiedRecordSetup() {
        loginAsAdmin()
        $j(MainScreen).with {
            sideMenu.openItem(INDIVIDUAL_BROWSE)
        }
        $j(IndividualBrowse).with {
            editIndividual(PETROV)
            $j(IndividualEditor).with {
                lastName.shouldBe(EDITABLE)
                        .setValue(NEW_PETROV)
                        .shouldHave(value(NEW_PETROV))
                saveChanges()
            }
            checkEntitySaved(PETR, NEW_PETROV)
        }
        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
        }
        $j(EntityLogBrowse).with {
            setEntity(INDIVIDUAL)
            setChangeType(MODIFY)
            clickSearch()
            selectLoggedEntity(MODIFY, INDIVIDUAL)
            checkAttrValue('Last name', NEW_PETROV)
        }
    }

    @Test
    @DisplayName("Checks removed and restored entity record")
    void checkRemovedAndRestoredRecord() {
        loginAsAdmin()
        //check remove entity log
        $j(MainScreen)
                .sideMenu
                .openItem(INDIVIDUAL_BROWSE)

        $j(IndividualBrowse).
                removeIndividual(FEDOROV)

        // Collapse menu before opening item from administration
        $j(MainScreen)
                .sideMenu.collapse()

        $j(MainScreen)
                .sideMenu
                .openItem(ENTITY_LOG_BROWSE)
        $j(EntityLogBrowse).with {
            setEntity(INDIVIDUAL)
            setChangeType(DELETE)
            clickSearch()
            checkIndividualRecord(DELETE, FEDOR, FEDOROV)
        }
        //check restore entity log
        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_INSPECTOR)
        }
        $j(EntityInspectorBrowse).with {
            entitiesLookup.shouldBe(ENABLED)
                    .setFilter(INDIVIDUAL)
                    .delegate
                    .$(byClassName('v-filterselect-input'))
                    .sendKeys(ENTER)
            showMode.shouldBe(ENABLED)
                    .openOptionsPopup()
                    .select(byText(SHOW_REMOVED_RECORDS))
                    .shouldHave(value(SHOW_REMOVED_RECORDS))
            individualTable.shouldBe(VISIBLE)
                    .selectRow(byText(FEDOROV))
                    .shouldBe(VISIBLE)
            restore.shouldBe(ENABLED)
                    .click()
            $j(OptionDialog).confirm()
        }
        $j(MainScreen)
                .sideMenu
                .openItem(ENTITY_LOG_BROWSE)

        $j(EntityLogBrowse).with {
            setEntity(INDIVIDUAL)
            setChangeType(RESTORE)
            clickSearch()
            checkIndividualRecord(RESTORE, FEDOR, FEDOROV)
        }
    }
}
