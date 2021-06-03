package io.jmix.tests.ui.test.audit

import io.jmix.masquerade.component.CheckBox
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.audit.EntityLogBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
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
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,entity-log'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class EntityLogSetupTest extends BaseUiTest {

    public static final String ATMOSPHERE = 'Atmosphere (Atmosphere)'
    public static final String ATMOSPHERIC_GAS = 'Atmospheric gas (AtmosphericGas)'
    public static final String ATMOSPHERIC_GAS_RECORD = 'AtmosphericGas'
    public static final String CARRIER = "Carrier (Carrier)"
    public static final String CARRIER_RECORD = "Carrier"
    public static final String DELETE_CONFIRMATION = 'Are you sure you want to delete selected elements?'
    public static final String ATMOSPHERE_RECORD = 'Atmosphere'

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
            selectSetupRecord(ATMOSPHERIC_GAS_RECORD)
            edit
                    .shouldBe(ENABLED)
                    .click()
            entityNameField
                    .shouldBe(VISIBLE)
                    .shouldHave(value(ATMOSPHERIC_GAS))
            autoCheckBox
                    .shouldBe(VISIBLE, CHECKED)
            setCheckbox(manualCheckBox, false)
            $j(CheckBox.class, 'volume')
                    .shouldBe(EDITABLE, CHECKED)
                    .setChecked(false)
                    .setChecked(false)
            $j(CheckBox.class, 'atmosphere')
                    .shouldNotBe(CHECKED)
                    .setChecked(true)
                    .shouldBe(CHECKED)
            saveSetup()
            selectSetupRecord(ATMOSPHERIC_GAS_RECORD)
            applyChanges()
            checkAppliedChangesNotification()
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
                createSetup()
                selectNameFromDropdown(ATMOSPHERIC_GAS)
                saveSetup()
                $('.v-Notification-error')
                        .shouldBe(VISIBLE)
                        .click()
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
