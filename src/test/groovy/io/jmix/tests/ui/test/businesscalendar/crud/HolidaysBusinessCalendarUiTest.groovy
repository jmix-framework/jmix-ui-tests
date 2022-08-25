package io.jmix.tests.ui.test.businesscalendar.crud

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.component.OptionsGroup
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
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Condition.cssClass
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
class HolidaysBusinessCalendarUiTest extends BusinessCalendarBaseUiTest {

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
    @DisplayName("Create Business Calendar with holiday type")
    void createBusinessCalendarWithDayOfWeek() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)

                $j(HolidayEditor).with {
                    clickButton(commitAndCloseBtn)
                    checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_HOLIDAY_TYPE_NOTIFICATION_CAPTION)
                    clickButton(closeBtn)
                }
                clickButton(create)
                createHolidaysWithDayOfWeek()

                checkRecordIsDisplayed(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DAY_OF_WEEK_SUNDAY, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DESCRIPTION_FIELD_VALUE, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit holidays with a day of the week")
    void editBusinessCalendarWithDayOfWeek() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)

                $j(HolidayEditor).with {
                    selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_DAY_OF_WEEK)

                    def optionsGroup = $j(OptionsGroup.class, 'dayOfWeekCheckboxGroup')
                            .shouldBe(VISIBLE)
                    optionsGroup
                            .shouldNotHave(cssClass('v-select-optiongroup-horizontal'))
                            .select(DAY_OF_WEEK_SATURDAY)
                            .select(DAY_OF_WEEK_SUNDAY)

                    descriptionField.shouldNotBe(Conditions.REQUIRED)
                    fillTextField(descriptionField, DESCRIPTION_FIELD_VALUE)
                    clickButton(ok)
                }
                checkRecordIsDisplayed(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DAY_OF_WEEK_SUNDAY, HOLIDAYS_TABLE_J_TEST_ID)

                checkRecordIsDisplayed(DESCRIPTION_FIELD_VALUE, HOLIDAYS_TABLE_J_TEST_ID)
            }
        }
    }

    @Test
    @DisplayName("Remove holidays with a day of the week")
    void removeBusinessCalendarWithDayOfWeek() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)

                clickButton(create)
                createHolidaysWithDayOfWeek()

                selectRowInTableByText(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                checkRecordIsNotDisplayed(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}
