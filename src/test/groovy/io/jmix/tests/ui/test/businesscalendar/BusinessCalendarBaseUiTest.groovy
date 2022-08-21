package io.jmix.tests.ui.test.businesscalendar

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.component.OptionsGroup
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.HolidayEditor
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

import io.jmix.tests.ui.test.BaseUiTest;
import io.jmix.tests.ui.test.utils.UiHelper

class BusinessCalendarBaseUiTest extends BaseUiTest implements UiHelper {

    protected static final String BUSINESS_CALENDAR_NAME = "Business Calendar "
    protected static final String BUSINESS_CALENDAR_CODE = "code-business-calendar-"
    protected static final String BUSINESS_CALENDAR_SOURCE = "Database"
    protected businessCalendars = []

    protected static final String DESCRIPTION_FIELD_VALUE = "Test Description 1 !"

    protected static final String BUSINESS_CALENDARS_TABLE_J_TEST_ID = "businessCalendarsTable"
    protected static final String HOLIDAYS_TABLE_J_TEST_ID = "holidaysTable"

    protected static final String ALERT_NOTIFICATION_CAPTION = "Alert"
    protected static final String REQUIRED_NAME_AND_CODE_NOTIFICATION_CAPTION = "Name required\nCode required"
    protected static final String REQUIRED_HOLIDAY_TYPE_NOTIFICATION_CAPTION = "The 'Holiday type' field is required"

    protected static final String HOLIDAY_TYPE_DAY_OF_WEEK = "Day of week"
    protected static final String DAY_OF_WEEK_MONDAY = "Monday"
    protected static final String DAY_OF_WEEK_SATURDAY = "Saturday"
    protected static final String DAY_OF_WEEK_SUNDAY = "Sunday"

    protected static String getUniqueName(String baseString) {
        return baseString + getGeneratedString()
    }

    protected void removeAllBusinessCalendars() {
        businessCalendars.forEach(businessCalendar -> {
            $j(BusinessCalendarsBrowse).with {
                selectRowInTableByText(businessCalendar as String, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
                clickButton(removeBtn)
            }
            $j(ConfirmationDialog).confirmChanges()
        })
    }

    protected static void createEntity(String name, String code) {
        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, name)

                codeField.shouldBe(Conditions.REQUIRED)
                fillTextField(codeField, code)

                clickButton(ok)
            }
        }
    }

    protected static void createHolidaysWithDayOfWeek() {
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
            clickButton(commitAndCloseBtn)
        }
    }
}
