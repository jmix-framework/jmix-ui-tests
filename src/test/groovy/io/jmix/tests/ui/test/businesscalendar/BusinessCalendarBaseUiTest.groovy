package io.jmix.tests.ui.test.businesscalendar

import io.jmix.businesscalendarui.screen.calendar.BusinessCalendarEdit;
import io.jmix.tests.ui.test.BaseUiTest;
import io.jmix.tests.ui.test.utils.UiHelper;

class BusinessCalendarBaseUiTest extends BaseUiTest implements UiHelper {

    public static final String BUSINESS_CALENDARS_TABLE_J_TEST_ID = "businessCalendarsTable"

    static String getUniqueName(String baseString) {
        return baseString + getGeneratedString()
    }

}
