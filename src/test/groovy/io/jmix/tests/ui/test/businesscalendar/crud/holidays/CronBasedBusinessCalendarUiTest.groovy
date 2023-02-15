package io.jmix.tests.ui.test.businesscalendar.crud.holidays

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
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "main.liquibase.contexts=base")
@ContextConfiguration(initializers = TestContextInitializer)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CronBasedBusinessCalendarUiTest extends BusinessCalendarBaseUiTest {

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
    @DisplayName("Create a Business Calendar with holidays where type is a cron-based holiday")
    void createBusinessCalendarWithCronBased() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)

                createCronBasedHolidayWithAllChecks()

                checkRecordIsDisplayed(HOLIDAY_TYPE_CRON_BASED, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(LAST_DAY_MONTH_CRON_EXPRESSION, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DESCRIPTION_FIELD_VALUE, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit holidays with a cron-based holiday")
    void editBusinessCalendarWithCronBased() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)
                createCronBasedHoliday()

                selectRowInTableByText(LAST_DAY_MONTH_CRON_EXPRESSION, HOLIDAYS_TABLE_J_TEST_ID)
                clickButton(edit)

                $j(HolidayEditor).with {
                    fillTextField(cronExpressionField, EVERY_SUNDAY_CRON_EXPRESSION)
                    clickButton(commitAndCloseBtn)
                }

                clickButton(edit)
                $j(HolidayEditor).with {
                    selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_ANNUAL)
                    selectValueInComboBox(monthField, ANNUAL_DECEMBER)
                    selectValueWithoutFilterInComboBox(dayField, ANNUAL_FIRST_DAY)
                    clickButton(commitAndCloseBtn)
                }

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Remove holidays with a cron-based holiday")
    void removeBusinessCalendarWithCronBased() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)
                createCronBasedHoliday()

                selectRowInTableByText(LAST_DAY_MONTH_CRON_EXPRESSION, HOLIDAYS_TABLE_J_TEST_ID)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                checkRecordIsNotDisplayed(LAST_DAY_MONTH_CRON_EXPRESSION, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}
