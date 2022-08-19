package io.jmix.tests.ui.test.businesscalendar

import io.jmix.tests.ui.test.BaseUiTest;
import io.jmix.tests.ui.test.utils.UiHelper;

class BusinessCalendarBaseUiTest extends BaseUiTest implements UiHelper {

    public static final String BUSINESS_CALENDARS_TABLE_J_TEST_ID = "businessCalendarsTable"

    public static final String ALERT_NOTIFICATION_CAPTION = "Alert"
    public static final String REQUIRED_NAME_AND_CODE_NOTIFICATION_CAPTION = "Name required\nCode required"

    static String getUniqueName(String baseString) {
        return baseString + getGeneratedString()
    }

}
