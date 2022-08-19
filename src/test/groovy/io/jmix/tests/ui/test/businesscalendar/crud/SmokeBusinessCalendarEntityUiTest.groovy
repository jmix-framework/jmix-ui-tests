package io.jmix.tests.ui.test.businesscalendar.crud

import io.jmix.masquerade.Conditions
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
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
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
        ])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContextInitializer)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SmokeBusinessCalendarEntityUiTest extends BusinessCalendarBaseUiTest {

    static final String BUSINESS_CALENDAR_NAME = "Business Calendar"
    static final String BUSINESS_CALENDAR_CODE = "code-business-calendar"
    static final String BUSINESS_CALENDAR_SOURCE = "Database"
    public businessCalendars = []

    @BeforeEach
    void beforeEachTest() {
        loginAsAdmin()
        $j(MainScreen).openBusinessCalendarsBrowse()
    }

    @AfterAll
    void afterAll() {
        loginAsAdmin()
        $j(MainScreen).openBusinessCalendarsBrowse()

        // removing all business calendars
        businessCalendars.forEach(businessCalendar -> {
            $j(BusinessCalendarsBrowse).with {
                selectRowInTableByText(businessCalendar as String, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
                clickButton(removeBtn)
            }
            $j(ConfirmationDialog).confirmChanges()
        })
    }

    @Test
    @DisplayName("Create Business Calendar")
    void createBusinessCalendar() {
        def businessCalendarNameFirst = getUniqueName(BUSINESS_CALENDAR_NAME)
        def businessCalendarCode = getUniqueName(BUSINESS_CALENDAR_CODE)
        def businessCalendarSource = BUSINESS_CALENDAR_SOURCE
        businessCalendars.add(businessCalendarNameFirst)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                clickButton(ok)
                checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_NAME_AND_CODE_NOTIFICATION_CAPTION)

                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, businessCalendarNameFirst)

                codeField.shouldBe(Conditions.REQUIRED)
                fillTextField(codeField, businessCalendarCode)

                clickButton(ok)
            }
            checkRecordIsDisplayed(businessCalendarNameFirst, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(businessCalendarSource, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit Business Calendar")
    void editBusinessCalendar() {
        def businessCalendarNameSecond = getUniqueName(BUSINESS_CALENDAR_NAME)
        def businessCalendarNameAnotherSecond = getUniqueName(BUSINESS_CALENDAR_NAME)
        def businessCalendarCode = getUniqueName(BUSINESS_CALENDAR_CODE)
        def businessCalendarSource = BUSINESS_CALENDAR_SOURCE
        businessCalendars.add(businessCalendarNameSecond)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, businessCalendarNameSecond)

                codeField.shouldBe(Conditions.REQUIRED)
                fillTextField(codeField, businessCalendarCode)

                clickButton(ok)
            }
            checkRecordIsDisplayed(businessCalendarNameSecond, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(businessCalendarSource, BUSINESS_CALENDARS_TABLE_J_TEST_ID)

            selectRowInTableByText(businessCalendarNameSecond, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            clickButton(editBtn)

            $j(BusinessCalendarEditor).with {
                nameField.shouldBe(Conditions.REQUIRED).shouldHave(value(businessCalendarNameSecond))
                fillTextField(nameField, businessCalendarNameAnotherSecond)
                clickButton(ok)
            }

            checkRecordIsDisplayed(businessCalendarNameAnotherSecond, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(businessCalendarSource, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit Business Calendar")
    void removeBusinessCalendar() {
        def businessCalendarNameThird = getUniqueName(BUSINESS_CALENDAR_NAME)
        def businessCalendarCode = getUniqueName(BUSINESS_CALENDAR_CODE)
        def businessCalendarSource = BUSINESS_CALENDAR_SOURCE

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, businessCalendarNameThird)

                codeField.shouldBe(Conditions.REQUIRED)
                fillTextField(codeField, businessCalendarCode)

                clickButton(ok)
            }
            checkRecordIsDisplayed(businessCalendarNameThird, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(businessCalendarSource, BUSINESS_CALENDARS_TABLE_J_TEST_ID)

            selectRowInTableByText(businessCalendarNameThird, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
            clickButton(removeBtn)

            $j(ConfirmationDialog).confirmChanges()

            checkRecordIsNotDisplayed(businessCalendarNameThird, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}
