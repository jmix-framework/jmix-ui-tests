package io.jmix.tests.ui.test.audit

import io.jmix.masquerade.component.CheckBox
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.audit.EntityLogBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import io.jmix.tests.ui.test.utils.UiHelper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.tests.ui.menu.Menus.ENTITY_LOG_BROWSE

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "main.liquibase.contexts=base")
@ContextConfiguration(initializers = TestContextInitializer)
class EntityLogSetupTest extends BaseUiTest implements UiHelper {

    public static final String ATMOSPHERE = 'Atmosphere (Atmosphere)'
    public static final String ATMOSPHERE_RECORD = 'Atmosphere'
    public static final String ATMOSPHERIC_GAS = 'Atmospheric gas (AtmosphericGas)'
    public static final String ATMOSPHERIC_GAS_RECORD = 'AtmosphericGas'
    public static final String CARRIER = "Carrier (Carrier)"
    public static final String CARRIER_RECORD = "Carrier"
    public static final String CATEGORY = "Category (dynat_Category)"
    public static final String CATEGORY_RECORD = "dynat_Category"
    public static final String DELETE_CONFIRMATION = 'Are you sure you want to delete selected elements?'

    @Test
    @DisplayName("Creates Setup record")
    void createSetupRecord() {
        loginAsAdmin()

        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
        }
        $j(EntityLogBrowse).with {
            openSetupTab()
            createSetup()
            selectNameFromDropdown(ATMOSPHERE)
            setCheckbox(autoCheckBox, true)
            setCheckbox(manualCheckBox, true)
            setCheckbox(selectAllCheckBox, true)
            $j(CheckBox.class, 'pressure')
                    .shouldBe(CHECKED)
            saveSetup()
            selectSetupRecord(ATMOSPHERE_RECORD)
            applyChanges()
            checkAppliedChangesNotification()
            clickButton(remove)
            $j(ConfirmationDialog).confirmChanges()
        }
    }

    @Test
    @DisplayName("Edits Setup record")
    void editSetupRecord() {
        loginAsAdmin()

        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
        }
        $j(EntityLogBrowse).with {
            openSetupTab()
            createSetup()
            selectNameFromDropdown(ATMOSPHERIC_GAS)
            setCheckbox(autoCheckBox, true)
            setCheckbox(volume, true)
            saveSetup()
            selectSetupRecord(ATMOSPHERIC_GAS_RECORD)
            clickButton(edit)
            entityNameField
                    .shouldBe(VISIBLE)
                    .shouldHave(value(ATMOSPHERIC_GAS))
            autoCheckBox
                    .shouldBe(VISIBLE, CHECKED)
            setCheckbox(manualCheckBox, false)
            $j(CheckBox.class, 'volume')
                    .shouldBe(EDITABLE, CHECKED)
                    .setChecked(false)
                    .shouldNotBe(CHECKED)
            $j(CheckBox.class, 'atmosphere')
                    .shouldNotBe(CHECKED)
                    .setChecked(true)
                    .shouldBe(CHECKED)
            saveSetup()
            selectSetupRecord(ATMOSPHERIC_GAS_RECORD)
            applyChanges()
            checkAppliedChangesNotification()
            clickButton(remove)
            $j(ConfirmationDialog).confirmChanges()
        }
    }

    @Test
    @DisplayName("Creates Setup record with the same name")
    void createSetupRecordWithTheSameName() {
        loginAsAdmin()
        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
            $j(EntityLogBrowse).with {
                openSetupTab()
                createSimpleEntity(CATEGORY)
                createSimpleEntity(CATEGORY)
                $('.v-Notification-error')
                        .shouldBe(VISIBLE)
                        .click()
                selectNameFromDropdown(CARRIER)
                saveSetup()
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                selectSetupRecord(CATEGORY_RECORD)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
            }
        }
    }

    @Test
    @DisplayName("Removes Setup record")
    void removeSetupRecord() {
        loginAsAdmin()
        $j(MainScreen).with {
            sideMenu.openItem(ENTITY_LOG_BROWSE)
            $j(EntityLogBrowse).with {
                openSetupTab()
                createSetup()
                selectNameFromDropdown(CARRIER)
                saveSetup()
                selectSetupRecord(CARRIER_RECORD)
                remove.shouldBe(ENABLED)
                        .click()
                $j(ConfirmationDialog)
                        .shouldBe(VISIBLE)
                        .shouldHave(textCaseSensitive(DELETE_CONFIRMATION))
                        .confirmChanges()
            }
        }
    }
}
