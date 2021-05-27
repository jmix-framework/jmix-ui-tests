package io.jmix.tests.ui.test.datatools.entitycreating

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.editors.DiscountsEditor
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class EntityWithEnumUiTests extends BaseDatatoolsUiTest {
    public static final String BRONZE_GRADE = "Bronze"
    public static final String SILVER_GRADE = "Silver"

    @Test
    @DisplayName("Creates entity with Enum from Entity Inspector Browser")
    void createEntity() {
        loginAsAdmin()

        openEntityCreatingScreen(DISCOUNTS_ENTITY_NAME, DISCOUNTS_FULL_STRING)

        $j(DiscountsEditor).with {
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            fillTextField(value, NON_DECIMAL_VALUE)
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            fillTextField(value, FIRST_DECIMAL_VALUE)
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION)

            selectGrade(BRONZE_GRADE)
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(BRONZE_GRADE, DISCOUNTS_TABLE_JTEST_ID)

        cleanData(DISCOUNTS_ENTITY_NAME, DISCOUNTS_FULL_STRING, ALL_MODE, DISCOUNTS_TABLE_JTEST_ID, BRONZE_GRADE)
    }

    @Test
    @DisplayName("Edits entity with Enum from Entity Inspector Browser")
    void editEntity() {
        loginAsAdmin()

        openEntityCreatingScreen(DISCOUNTS_ENTITY_NAME, DISCOUNTS_FULL_STRING)

        $j(DiscountsEditor).with {
            selectGrade(BRONZE_GRADE)
            fillTextField(value, FIRST_DECIMAL_VALUE)
            clickButton(ok)
        }

        $j(EntityInspectorBrowse).with {
            checkRecordIsDisplayed(BRONZE_GRADE, DISCOUNTS_TABLE_JTEST_ID)
            selectRowInTableByText(BRONZE_GRADE, DISCOUNTS_TABLE_JTEST_ID)
            clickButton(edit)
        }
        $j(DiscountsEditor).with {
            selectGrade(SILVER_GRADE)
            clickButton(ok)
        }
        $j(EntityInspectorBrowse).checkRecordIsDisplayed(SILVER_GRADE, DISCOUNTS_TABLE_JTEST_ID)

        cleanData(DISCOUNTS_ENTITY_NAME, DISCOUNTS_FULL_STRING, ALL_MODE, DISCOUNTS_TABLE_JTEST_ID, SILVER_GRADE)
    }
}
