package io.jmix.tests.ui.test.businesscalendar.crud.workingschedule

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.WorkingScheduleEditor
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.businesscalendar.BusinessCalendarBaseUiTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.VISIBLE
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
class WorkingScheduleBusinessCalendarUiTest extends BusinessCalendarBaseUiTest {

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
    @DisplayName("Create a Business Calendar with working schedule")
    void createBusinessCalendarWorkingSchedule() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)
                workingScheduleTabId.shouldBe(VISIBLE)
                        .getDelegate()
                        .click()

                clickButton(create)

                $j(WorkingScheduleEditor).with {
                    clickButton(commitAndCloseBtn)
                    checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_DAY_HOURS_NOTIFICATION_CAPTION)
                    clickButton(closeBtn)
                }
                clickButton(create)
                createWorkingSchedule()

                checkRecordIsDisplayed(DAY_OF_WEEK_MONDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                checkRecordIsDisplayed(DAY_OF_WEEK_SATURDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                checkRecordIsDisplayed(DAY_OF_WEEK_SUNDAY, SCHEDULED_BUSINESS_DAYS_TABLE)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit a Business Calendar with working schedule")
    void editBusinessCalendarWorkingSchedule() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)
                workingScheduleTabId.shouldBe(VISIBLE)
                        .getDelegate()
                        .click()

                clickButton(create)
                createWorkingScheduleWithIntervals()

                selectRowInTableByText(DAY_OF_WEEK_SUNDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                clickButton(edit)

                editWorkingSchedule()
                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Remove days of working schedule")
    void removeBusinessCalendarWorkingSchedule() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)
                workingScheduleTabId.shouldBe(VISIBLE)
                        .getDelegate()
                        .click()

                clickButton(create)
                createWorkingSchedule()

                removeWorkingScheduleDays()

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}
