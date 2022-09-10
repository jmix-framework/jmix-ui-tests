package io.jmix.tests.ui.test.businesscalendar.crud

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.HolidayEditor
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.businesscalendar.BusinessCalendarBaseUiTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

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
class SpecificDateBusinessCalendarUiTest extends BusinessCalendarBaseUiTest {

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
    @DisplayName("Create a Business Calendar with holidays where type is a Specific date holiday")
    void createBusinessCalendarWithSpecificDate() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)

                createSpecificDateHoliday()

                checkRecordIsDisplayed(HOLIDAY_TYPE_SPECIFIC_DATE, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DATE_VALUE_FIELD, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DESCRIPTION_FIELD, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit holidays with a Specific date holiday")
    void editBusinessCalendarWithSpecificDate() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)
                createSpecificDateHoliday()

                selectRowInTableByText(DATE_VALUE_FIELD, HOLIDAYS_TABLE_J_TEST_ID)
                clickButton(edit)

                $j(HolidayEditor).with {
                    fillTextField(descriptionField, ANOTHER_DESCRIPTION_FIELD)
                    fixedDateField.setDateValue(ANOTHER_DATE_FIELD)
                    clickButton(commitAndCloseBtn)
                }

                clickButton(edit)
                $j(HolidayEditor).with {
                    selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_DAY_OF_WEEK)
                    selectValueWithoutFilterInComboBox(dayOfWeek, DAY_OF_WEEK_MONDAY)
                    fillTextField(descriptionField, ANOTHER_DESCRIPTION_FIELD)
                    clickButton(commitAndCloseBtn)
                }

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Remove holidays with a Specific date holiday")
    void removeBusinessCalendarWithSpecificDate() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)
                createSpecificDateHoliday()

                selectRowInTableByText(DATE_VALUE_FIELD, HOLIDAYS_TABLE_J_TEST_ID)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                checkRecordIsNotDisplayed(DATE_VALUE_FIELD, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}
