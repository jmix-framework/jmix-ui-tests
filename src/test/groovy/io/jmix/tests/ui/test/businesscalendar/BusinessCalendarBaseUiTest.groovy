package io.jmix.tests.ui.test.businesscalendar

import io.jmix.masquerade.Conditions
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.test.BaseUiTest;
import io.jmix.tests.ui.test.utils.UiHelper

import static io.jmix.masquerade.Selectors.$j

class BusinessCalendarBaseUiTest extends BaseUiTest implements UiHelper {

    protected static final String BUSINESS_CALENDAR_NAME = "Business Calendar "
    protected static final String BUSINESS_CALENDAR_CODE = "code-business-calendar-"
    protected static final String BUSINESS_CALENDAR_SOURCE = "Database"
    protected businessCalendars = []

    protected static final String DESCRIPTION_FIELD = "Test Description 1 !"

    protected static final String BUSINESS_CALENDARS_TABLE_J_TEST_ID = "businessCalendarsTable"
    protected static final String HOLIDAYS_TABLE_J_TEST_ID = "holidaysTable"

    protected static final String ALERT_NOTIFICATION_CAPTION = "Alert"
    protected static final String REQUIRED_NAME_AND_CODE_NOTIFICATION_CAPTION = "Name required\nCode required"

    protected static final String HOLIDAY_TYPE_DAY_OF_WEEK = "Day of week"
    protected static final String DAY_OF_WEEK_SATURDAY = "Saturday"
    protected static final String DAY_OF_WEEK_SUNDAY = "Sunday"

    protected static String getUniqueName(String baseString) {
        return baseString + getGeneratedString()
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
}
