package io.jmix.tests.ui.test.businesscalendar.crud

import io.jmix.masquerade.Conditions
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.businesscalendars.browse.BusinessCalendarsBrowse
import io.jmix.tests.ui.screen.administration.businesscalendars.editor.BusinessCalendarEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.businesscalendar.BusinessCalendarBaseUiTest
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

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
class SmokeBusinessCalendarEntityUiTest extends BusinessCalendarBaseUiTest {

    static final String BUSINESS_CALENDAR_NAME = "Business Calendar"
    static final String BUSINESS_CALENDAR_CODE = "code-business-calendar"
    public businessCalendars = []

    @BeforeEach
    void beforeEachTest() {
        loginAsAdmin()
        $j(MainScreen).openBusinessCalendarsBrowse()
    }

    @Test
    @DisplayName("Create Business Calendar")
    void createBusinessCalendar() {
        def businessCalendarName = getUniqueName(BUSINESS_CALENDAR_NAME);
        def businessCalendarCode = getUniqueName(BUSINESS_CALENDAR_CODE);
//        businessCalendars.add(businessCalendarName)

        $j(BusinessCalendarsBrowse).with {
            clickButton(createBtn)

            $j(BusinessCalendarEditor).with {
                nameField.shouldBe(Conditions.REQUIRED)
                fillTextField(nameField, businessCalendarName)

                codeField.shouldBe(Conditions.REQUIRED)
                fillTextField(codeField, businessCalendarCode)

                clickButton(ok)
            }
            checkRecordIsDisplayed(businessCalendarName, BUSINESS_CALENDARS_TABLE_J_TEST_ID)
        }
    }

    @Test
    @DisplayName("Edit Business Calendar")
    void editBusinessCalendar() {
        // ...
    }
}
