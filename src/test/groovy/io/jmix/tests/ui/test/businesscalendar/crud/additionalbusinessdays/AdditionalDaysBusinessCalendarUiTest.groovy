package io.jmix.tests.ui.test.businesscalendar.crud.additionalbusinessdays

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.dialogs.AdditionalBusinessDayEditor
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
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "main.liquibase.contexts=base")
@ContextConfiguration(initializers = TestContextInitializer)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdditionalDaysBusinessCalendarUiTest extends BusinessCalendarBaseUiTest {

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
    @DisplayName("Create a Business Calendar with additional business days")
    void createBusinessCalendarWithAdditionalDays() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)
                additionalBusinessDaysTabId.shouldBe(VISIBLE)
                        .getDelegate()
                        .click()

                clickButton(create)

                $j(AdditionalBusinessDayEditor).with {
                    clickButton(commitAndCloseBtn)
                    checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_DATE_AND_TIME_NOTIFICATION_CAPTION)
                    clickButton(closeBtn)
                }
                clickButton(create)
                createAdditionalBusinessDayWithAllChecks()

                clickButton(create)
                createAdditionalBusinessDay()

                checkRecordIsDisplayed(DATE_FIELD_FORMATTED_VALUE, ADDITIONAL_BUSINESS_DAYS_TABLE_J_TEST_ID)

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit a Business Calendar with additional business days")
    void editBusinessCalendarWithAdditionalDays() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)
                additionalBusinessDaysTabId.shouldBe(VISIBLE)
                        .getDelegate()
                        .click()

                clickButton(create)
                createAdditionalBusinessDay()

                selectRowInTableByText(DATE_FIELD_FORMATTED_VALUE, ADDITIONAL_BUSINESS_DAYS_TABLE_J_TEST_ID)
                clickButton(edit)

                editAdditionalBusinessDay()

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Remove additional business day")
    void removeBusinessCalendarWithAdditionalDays() {
        String name = getUniqueName(BUSINESS_CALENDAR_NAME)
        String code = getUniqueName(BUSINESS_CALENDAR_CODE)
        businessCalendars.add(name)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                fillTextField(nameField, name)
                fillTextField(codeField, code)
                additionalBusinessDaysTabId.shouldBe(VISIBLE)
                        .getDelegate()
                        .click()

                clickButton(create)
                createAdditionalBusinessDay()

                removeAdditionalBusinessDays()

                clickButton(ok)
            }
            checkRecordIsDisplayed(name, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }
}
