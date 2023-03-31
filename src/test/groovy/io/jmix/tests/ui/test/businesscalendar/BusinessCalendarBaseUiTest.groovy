package io.jmix.tests.ui.test.businesscalendar

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.component.OptionsGroup
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.AdditionalBusinessDayEditor
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.HolidayEditor
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.WorkingScheduleEditor
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.test.BaseLoginUiTest
import io.jmix.tests.ui.test.utils.UiHelper

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

class BusinessCalendarBaseUiTest extends BaseLoginUiTest implements UiHelper {

    protected static final String BUSINESS_CALENDAR_NAME = "Business Calendar "
    protected static final String BUSINESS_CALENDAR_CODE = "code-business-calendar-"
    protected static final String BUSINESS_CALENDAR_SOURCE = "Database"
    protected businessCalendars = []

    protected static final String DESCRIPTION_FIELD_VALUE = "Test Description 1 !"

    protected static final String DATE_FIELD_RAW_VALUE = "01012022"
    protected static final String DATE_FIELD_FORMATTED_VALUE = "2022-01-01"
    protected static final String FIXED_DATE_FIELD_RAW_VALUE = "29042022"

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
    protected static final String LAST_DAY_MONTH_CRON_EXPRESSION = "* * * L * ?"
    protected static final String EVERY_SUNDAY_CRON_EXPRESSION = "* * * ? * SUN"
    protected static final String NOT_VALID_CRON_EXPRESSION = "* ! * L * ?"
    protected static final String TEXT_NOT_VALID_CRON_EXPRESSION = "not valid cron"
    protected static final String NUMBER_NOT_VALID_CRON_EXPRESSION = "6699"
    protected static final String INCORRECT_CRON_NOTIFICATION = "Incorrect cron expression"
    protected static final String SPECIFY_VALID_EXPRESSION_NOTIFICATION = "Please specify valid quartz cron expression"

    protected static final String SCHEDULED_BUSINESS_DAYS_TABLE = "scheduledBusinessDaysTable"
    protected static final String INCORRECT_TIME_NOTIFICATION = "Incorrect time settings"
    protected static final String VALID_TIME_EXPRESSION_NOTIFICATION = "Start time should be before End time"
    protected static final String REQUIRED_DAY_HOURS_NOTIFICATION_CAPTION = "The 'Day of week' field is required" +
            "\nThe field is required" +
            "\nThe field is required"
    protected static final String INCORRECT_TIME_SETTINGS_NOTIFICATION = "Incorrect working time settings"
    protected static final String PERIODS_NOT_INTERSECTION_NOTIFICATION = "Working periods should not have intersection"
    protected static final String START_TIME_VALUE_1200 = "1200"
    protected static final String END_TIME_VALUE_1800 = "1800"
    protected static final String START_TIME_VALUE_2000 = "2000"
    protected static final String END_TIME_VALUE_2300 = "2300"
    protected static final String START_TIME_VALUE_0000 = "0000"
    protected static final String END_TIME_VALUE_0500 = "0500"

    protected static final String ADDITIONAL_BUSINESS_DAYS_TABLE_J_TEST_ID = "additionalBusinessDaysTable"
    protected static final String REQUIRED_DATE_AND_TIME_NOTIFICATION_CAPTION = "Date required" +
            "\nStart time required" +
            "\nEnd time required"

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
            fillTextField(descriptionField, DESCRIPTION_FIELD_VALUE)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createSpecificDateHoliday() {
        $j(HolidayEditor).with {
            selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_SPECIFIC_DATE)

            clickButton(commitAndCloseBtn)
            checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_HOLIDAY_DATE_NOTIFICATION_CAPTION)

            fixedDateField.shouldBe(VISIBLE)
                    .setDateValue(DATE_FIELD_RAW_VALUE)

            descriptionField.shouldNotBe(Conditions.REQUIRED)
            fillTextField(descriptionField, DESCRIPTION_FIELD_VALUE)
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
            fillTextField(descriptionField, DESCRIPTION_FIELD_VALUE)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createCronBasedHoliday() {
        $j(HolidayEditor).with {
            selectValueWithoutFilterInComboBox(holidayType, HOLIDAY_TYPE_CRON_BASED)
            fillTextField(cronExpressionField, LAST_DAY_MONTH_CRON_EXPRESSION)
            fillTextField(descriptionField, DESCRIPTION_FIELD_VALUE)
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

            fillTextField(cronExpressionField, LAST_DAY_MONTH_CRON_EXPRESSION)
            descriptionField.shouldNotBe(Conditions.REQUIRED)
            fillTextField(descriptionField, DESCRIPTION_FIELD_VALUE)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createWorkingSchedule() {
        $j(WorkingScheduleEditor).with {
            def optionsGroup = $j(OptionsGroup.class, 'dayOfWeekCheckBoxGroup')
                    .shouldBe(VISIBLE)
            optionsGroup
                    .shouldNotHave(cssClass('v-select-optiongroup-horizontal'))
                    .select(DAY_OF_WEEK_MONDAY)
                    .select(DAY_OF_WEEK_SATURDAY)
                    .select(DAY_OF_WEEK_SUNDAY)

            startTimeField.shouldBe(Conditions.REQUIRED)
            startTimeField.setTimeValue(END_TIME_VALUE_1800)
            endTimeField.shouldBe(Conditions.REQUIRED)
            endTimeField.setTimeValue(START_TIME_VALUE_1200)
            clickButton(commitAndCloseBtn)
            checkNotification(INCORRECT_TIME_NOTIFICATION, VALID_TIME_EXPRESSION_NOTIFICATION)

            startTimeField.setTimeValue(START_TIME_VALUE_1200)
            endTimeField.setTimeValue(END_TIME_VALUE_1800)

            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createWorkingScheduleWithIntervals() {
        $j(WorkingScheduleEditor).with {
            $j(OptionsGroup.class, 'dayOfWeekCheckBoxGroup')
                    .select(DAY_OF_WEEK_SUNDAY)
            startTimeField.setTimeValue(START_TIME_VALUE_1200)
            endTimeField.setTimeValue(END_TIME_VALUE_1800)

            clickButton(addAnotherIntervalLinkButton)
            deleteIcon.shouldBe(VISIBLE)
            clickButton(deleteIcon)

            clickButton(addAnotherIntervalLinkButton)
            firstIntervalStartTimeField.shouldBe(VISIBLE).setTimeValue(START_TIME_VALUE_2000)
            firstIntervalEndTimeField.shouldBe(VISIBLE).setTimeValue(END_TIME_VALUE_2300)

            clickButton(addAnotherIntervalLinkButton)
            secondIntervalStartTimeField.shouldBe(VISIBLE).setTimeValue(START_TIME_VALUE_2000)
            secondIntervalEndTimeField.shouldBe(VISIBLE).setTimeValue(END_TIME_VALUE_2300)
            clickButton(commitAndCloseBtn)
            checkNotification(INCORRECT_TIME_SETTINGS_NOTIFICATION, PERIODS_NOT_INTERSECTION_NOTIFICATION)

            clickButton(deleteIcon)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void editWorkingSchedule() {
        $j(WorkingScheduleEditor).with {
            selectValueWithoutFilterInComboBox(dayOfWeekSchedule, DAY_OF_WEEK_MONDAY)
            clickButton(addAnotherIntervalLinkButton)
            secondIntervalStartTimeField.shouldBe(VISIBLE).setTimeValue(START_TIME_VALUE_0000)
            secondIntervalEndTimeField.shouldBe(VISIBLE).setTimeValue(END_TIME_VALUE_0500)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void removeWorkingScheduleDays() {
        $j(BusinessCalendarsBrowse).with {
            $j(BusinessCalendarEditor).with {
                selectRowInTableByText(DAY_OF_WEEK_MONDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                selectRowInTableByText(DAY_OF_WEEK_SATURDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                selectRowInTableByText(DAY_OF_WEEK_SUNDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                checkRecordIsNotDisplayed(DAY_OF_WEEK_MONDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                checkRecordIsNotDisplayed(DAY_OF_WEEK_SATURDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
                checkRecordIsNotDisplayed(DAY_OF_WEEK_SUNDAY, SCHEDULED_BUSINESS_DAYS_TABLE)
            }
        }
    }

    protected static void createAdditionalBusinessDay() {
        $j(AdditionalBusinessDayEditor).with {
            fixedDateField.shouldBe(VISIBLE)
                    .setDateValue(DATE_FIELD_RAW_VALUE)
            startTimeField.setTimeValue(START_TIME_VALUE_2000)
            endTimeField.setTimeValue(END_TIME_VALUE_2300)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void createAdditionalBusinessDayWithAllChecks() {
        $j(AdditionalBusinessDayEditor).with {
            fixedDateField.shouldBe(VISIBLE)
                    .setDateValue(DATE_FIELD_RAW_VALUE)

            startTimeField.shouldBe(Conditions.REQUIRED)
            startTimeField.setTimeValue(END_TIME_VALUE_1800)
            endTimeField.shouldBe(Conditions.REQUIRED)
            endTimeField.setTimeValue(START_TIME_VALUE_1200)
            clickButton(commitAndCloseBtn)
            checkNotification(INCORRECT_TIME_NOTIFICATION, VALID_TIME_EXPRESSION_NOTIFICATION)

            startTimeField.setTimeValue(START_TIME_VALUE_1200)
            endTimeField.setTimeValue(END_TIME_VALUE_1800)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void editAdditionalBusinessDay() {
        $j(AdditionalBusinessDayEditor).with {
            fixedDateField.setDateValue(FIXED_DATE_FIELD_RAW_VALUE)
            startTimeField.setTimeValue(START_TIME_VALUE_0000)
            endTimeField.setTimeValue(END_TIME_VALUE_0500)
            clickButton(commitAndCloseBtn)
        }
    }

    protected static void removeAdditionalBusinessDays() {
        $j(BusinessCalendarsBrowse).with {
            $j(BusinessCalendarEditor).with {
                selectRowInTableByText(DATE_FIELD_FORMATTED_VALUE, ADDITIONAL_BUSINESS_DAYS_TABLE_J_TEST_ID)
                clickButton(remove)
                $j(ConfirmationDialog).confirmChanges()
                checkRecordIsNotDisplayed(DATE_FIELD_FORMATTED_VALUE, ADDITIONAL_BUSINESS_DAYS_TABLE_J_TEST_ID)
            }
        }
    }
}
