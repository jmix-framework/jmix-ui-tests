package io.jmix.tests.ui.test.businesscalendar

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.component.OptionsGroup
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.HolidayEditor
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.test.BaseUiTest;
import io.jmix.tests.ui.test.utils.UiHelper

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

class BusinessCalendarBaseUiTest extends BaseUiTest implements UiHelper {

    protected static final String BUSINESS_CALENDAR_NAME = "Business Calendar "
    protected static final String BUSINESS_CALENDAR_CODE = "code-business-calendar-"
    protected static final String BUSINESS_CALENDAR_SOURCE = "Database"
    protected businessCalendars = []

    protected static final String DESCRIPTION_FIELD = "Test Description 1 !"
    protected static final String ANOTHER_DESCRIPTION_FIELD = "tstng-dscrptn"

    protected static final String DATE_FIELD = "01012022"
    protected static final String DATE_VALUE_FIELD = "2022-01-01"
    protected static final String ANOTHER_DATE_FIELD = "29042022"

    protected static final String BUSINESS_CALENDARS_TABLE_J_TEST_ID = "businessCalendarsTable"
    protected static final String HOLIDAYS_TABLE_J_TEST_ID = "holidaysTable"

    protected static final String ALERT_NOTIFICATION_CAPTION = "Alert"
    protected static final String REQUIRED_NAME_AND_CODE_NOTIFICATION_CAPTION = "Name required\nCode required"
    protected static final String REQUIRED_HOLIDAY_TYPE_NOTIFICATION_CAPTION = "The 'Holiday type' field is required"
    protected static final String REQUIRED_HOLIDAY_DATE_NOTIFICATION_CAPTION = "Date required"
    protected static final String REQUIRED_MONTH_AND_DAY_NOTIFICATION_CAPTION = "The 'Month' field is required\nDay required"
    protected static final String REQUIRED_CRON_NOTIFICATION_CAPTION = "Cron expression required"

    protected static final String HOLIDAY_TYPE_DAY_OF_WEEK = "Day of week"
    protected static final String DAY_OF_WEEK_MONDAY = "Monday"
    protected static final String DAY_OF_WEEK_SATURDAY = "Saturday"
    protected static final String DAY_OF_WEEK_SUNDAY = "Sunday"

    protected static final String HOLIDAY_TYPE_SPECIFIC_DATE = "Specific date holiday"

    protected static final String HOLIDAY_TYPE_ANNUAL = "Annual holiday"
    protected static final String ANNUAL_APRIL = "April"
    protected static final String ANNUAL_DECEMBER = "December"
    protected static final String ANNUAL_FIRST_DAY = "1"
    protected static final String ANNUAL_LAST_DAY = "31"
    protected static final String ANNUAL_MONTH_DAY_VALUE = "December 31"

    protected static final String HOLIDAY_TYPE_CRON_BASED = "Cron-based holiday"
    protected static final String VALID_CRON_EXPRESSION = "* * * L * ?"
    protected static final String ANOTHER_VALID_CRON_EXPRESSION = "* * * ? * SUN"
    protected static final String NOT_VALID_CRON_EXPRESSION = "* ! * L * ?"
    protected static final String TEXT_NOT_VALID_CRON_EXPRESSION = "not valid cron"
    protected static final String NUMBER_NOT_VALID_CRON_EXPRESSION = "6699"
    protected static final String INCORRECT_CRON_NOTIFICATION = "Incorrect cron expression"
    protected static final String SPECIFY_VALID_EXPRESSION_NOTIFICATION = "Please specify valid quartz cron expression"

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

    protected static void createDayOfWeekHoliday() {
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
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createSpecificDateHoliday() {
        $j(HolidayEditor).with {
            selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_SPECIFIC_DATE)

            clickButton(commitAndCloseBtn)
            checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_HOLIDAY_DATE_NOTIFICATION_CAPTION)

            fixedDateField.shouldBe(VISIBLE)
                    .setDateValue(DATE_FIELD)

            descriptionField.shouldNotBe(Conditions.REQUIRED)
            fillTextField(descriptionField, DESCRIPTION_FIELD)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createAnnualHoliday() {
        $j(HolidayEditor).with {
            selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_ANNUAL)

            clickButton(commitAndCloseBtn)
            checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_MONTH_AND_DAY_NOTIFICATION_CAPTION)

            selectValueInComboBox(monthField, ANNUAL_DECEMBER)
            selectValueInComboBox(dayField, ANNUAL_LAST_DAY)

            descriptionField.shouldNotBe(Conditions.REQUIRED)
            fillTextField(descriptionField, DESCRIPTION_FIELD)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createCronBasedHoliday() {
        $j(HolidayEditor).with {
            selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_CRON_BASED)
            fillTextField(cronExpressionField, VALID_CRON_EXPRESSION)
            fillTextField(descriptionField, DESCRIPTION_FIELD)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createCronBasedHolidayWithAllChecks() {
        $j(HolidayEditor).with {
            selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_CRON_BASED)

            clickButton(commitAndCloseBtn)
            checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_CRON_NOTIFICATION_CAPTION)

            cronExpressionField.shouldBe(Conditions.REQUIRED)
            fillTextField(cronExpressionField, NOT_VALID_CRON_EXPRESSION)
            clickButton(commitAndCloseBtn)
            checkNotification(INCORRECT_CRON_NOTIFICATION, SPECIFY_VALID_EXPRESSION_NOTIFICATION)

            fillTextField(cronExpressionField, TEXT_NOT_VALID_CRON_EXPRESSION)
            clickButton(commitAndCloseBtn)
            checkNotification(INCORRECT_CRON_NOTIFICATION, SPECIFY_VALID_EXPRESSION_NOTIFICATION)

            fillTextField(cronExpressionField, NUMBER_NOT_VALID_CRON_EXPRESSION)
            clickButton(commitAndCloseBtn)
            checkNotification(INCORRECT_CRON_NOTIFICATION, SPECIFY_VALID_EXPRESSION_NOTIFICATION)

            fillTextField(cronExpressionField, VALID_CRON_EXPRESSION)
            descriptionField.shouldNotBe(Conditions.REQUIRED)
            fillTextField(descriptionField, DESCRIPTION_FIELD)
            clickButton(commitAndCloseBtn)
        }
    }
}
