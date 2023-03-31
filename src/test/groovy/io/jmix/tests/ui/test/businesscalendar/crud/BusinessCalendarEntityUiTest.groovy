package io.jmix.tests.ui.test.businesscalendar.crud

import io.jmix.masquerade.Conditions
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.businesscalendar.BusinessCalendarBaseUiTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BusinessCalendarEntityUiTest extends BusinessCalendarBaseUiTest {

    @BeforeEach
    void beforeEachTest() {
        loginAsAdmin()
        $j(MainScreen).openBusinessCalendarsBrowse()
    }

    @AfterAll
    void afterAll() {
        loginAsAdmin()
        $j(MainScreen).openBusinessCalendarsBrowse()
        removeAllBusinessCalendars()
        $j(MainScreen).logout()
    }

    @Test
    @DisplayName("Create Business Calendar")
    void createBusinessCalendar() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                clickButton(ok)
                checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_NAME_AND_CODE_NOTIFICATION_CAPTION)
                clickButton(close)
            }
        }

        createEntity(name, code)

        $j(BusinessCalendarsBrowse).with {
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(BUSINESS_CALENDAR_SOURCE, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit Business Calendar")
    void editBusinessCalendar() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String modifiedName = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(modifiedName)

        createEntity(name, code)

        $j(BusinessCalendarsBrowse).with {
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(BUSINESS_CALENDAR_SOURCE, BUSINESS_CALENDARS_TABLE_J_TEST_ID)

            selectRowInTableByText(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            clickButton(editBtn)

            $j(BusinessCalendarEditor).with {
                nameField.shouldBe(Conditions.REQUIRED).shouldHave(value(name))
                fillTextField(nameField, modifiedName)
                clickButton(ok)
            }

            checkRecordIsDisplayed(modifiedName, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(BUSINESS_CALENDAR_SOURCE, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Remove Business Calendar")
    void removeBusinessCalendar() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)

        createEntity(name, code)

        $j(BusinessCalendarsBrowse).with {
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(BUSINESS_CALENDAR_SOURCE, BUSINESS_CALENDARS_TABLE_J_TEST_ID)

            selectRowInTableByText(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            clickButton(removeBtn)

            $j(ConfirmationDialog).confirmChanges()

            checkRecordIsNotDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}