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
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor.RED

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

    public static final boolean checkedCheckbox = true;

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
    @DisplayName("Create Business Calendar with holiday type")
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
                    selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_DAY_OF_WEEK)

                    def optionsGroup = $j(OptionsGroup.class, 'dayOfWeekCheckboxGroup')
                            .shouldBe(VISIBLE)
                    optionsGroup
                            .shouldNotHave(cssClass('v-select-optiongroup-horizontal'))
                            .select(DAY_OF_WEEK_SATURDAY)
                            .select(DAY_OF_WEEK_SUNDAY)

                    descriptionField.shouldNotBe(Conditions.REQUIRED)
                    fillTextField(descriptionField, DESCRIPTION_FIELD)
                    clickButton(ok)
                }
                checkRecordIsDisplayed(DAY_OF_WEEK_SATURDAY, HOLIDAYS_TABLE_J_TEST_ID)
                checkRecordIsDisplayed(DAY_OF_WEEK_SUNDAY, HOLIDAYS_TABLE_J_TEST_ID)

                checkRecordIsDisplayed(DESCRIPTION_FIELD, HOLIDAYS_TABLE_J_TEST_ID)
            }
        }
    }
}
