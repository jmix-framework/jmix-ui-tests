package io.jmix.tests.ui.test.businesscalendar.crud

import com.codeborne.selenide.SelenideElement
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

import static com.codeborne.selenide.Selenide.$x
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
    private static final SelenideElement okBtn = $x("//*[@class=\'v-horizontallayout v-layout v-horizontal v-widget v-has-height\']" +
            "//*[span=\'OK\']")
    private static final SelenideElement closeBtn = $x("//*[@class=\'v-horizontallayout v-layout v-horizontal v-widget v-has-height\']" +
            "//*[span=\'Close\']")

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
    @DisplayName("Create a Business Calendar with holidays where type is a Day of week")
    void createBusinessCalendarWithDayOfWeek() {
        String businessCalendarName = getUniqueName(BUSINESS_CALENDAR_NAME)
        String businessCalendarCode = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(businessCalendarName)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, businessCalendarName)
                fillTextField(codeField, businessCalendarCode)

                clickButton(create)

                $j(HolidayEditor).with {
                    okBtn.click()
                    checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_HOLIDAY_TYPE_NOTIFICATION_CAPTION)
                    closeBtn.shouldBe(VISIBLE)
                            .click()
                }
                clickButton(create)
                createHolidaysWithDayOfWeek(okBtn)

                checkRecordIsDisplayed(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DAY_OF_WEEK_SUNDAY, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DESCRIPTION_FIELD, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(businessCalendarName, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit holidays with a day of the week")
    void editBusinessCalendarWithDayOfWeek() {
        String businessCalendarName = getUniqueName(BUSINESS_CALENDAR_NAME)
        String businessCalendarCode = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(businessCalendarName)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, businessCalendarName)
                fillTextField(codeField, businessCalendarCode)

                clickButton(create)
                createHolidaysWithDayOfWeek(okBtn)

                selectRowInTableByText(DAY_OF_WEEK_SUNDAY, HOLIDAYS_TABLE_J_TEST_ID)
                clickButton(edit)

                $j(HolidayEditor).with {
                    fillTextField(descriptionField, ANOTHER_DESCRIPTION_FIELD)
                    selectValueWithoutFilterInComboBox(dayOfWeek, DAY_OF_WEEK_MONDAY)
                    okBtn.click()
                }
                clickButton(ok)
            }
            checkRecordIsDisplayed(businessCalendarName, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Remove holidays with a day of the week")
    void removeBusinessCalendarWithDayOfWeek() {
        String businessCalendarName = getUniqueName(BUSINESS_CALENDAR_NAME)
        String businessCalendarCode = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(businessCalendarName)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, businessCalendarName)
                fillTextField(codeField, businessCalendarCode)

                clickButton(create)
                createHolidaysWithDayOfWeek(okBtn)

                selectRowInTableByText(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                checkRecordIsNotDisplayed(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(businessCalendarName, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}
