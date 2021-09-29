package io.jmix.tests.ui.test.datatools.dialog

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.dialogs.EntityInformationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContextInitializer)
class EntityInfoDialogButtonsUiTest extends BaseDatatoolsUiTest {
    public static final String INSERT_BTN_CAPTION = "Script for insert"
    public static final String SELECT_BTN_CAPTION = "Script for select"
    public static final String UPDATE_BTN_CAPTION = "Script for update"
    public static final String NOTIFICATION_CAPTION = "Copied to clipboard"
    public static final String SCRIPT_AREA = "scriptArea"

    void commonInspectorActions() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectRowInTableByText(ADMIN_USERNAME, USER_TABLE_JTEST_ID)
        }

        openInspectorWindow(4)
    }

    @Test
    @DisplayName("Checks copying of Insert script from Entity Inspector Window")
    void checkAndCopyInsertScript() {
        commonInspectorActions()

        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)

            checkAndClickInsertBtn(INSERT_BTN_CAPTION)
            checkScriptArea(SCRIPT_AREA, "insert")
            checkAndClickCopyBtn()
        }
        checkNotification(NOTIFICATION_CAPTION)
        closeInspectorWindow()
    }

    @Test
    @DisplayName("Checks copying of Select script from Entity Inspector Window")
    void checkAndCopySelectScript() {
        commonInspectorActions()

        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)

            checkAndClickSelectBtn(SELECT_BTN_CAPTION)
            checkScriptArea(SCRIPT_AREA, "select")
            checkAndClickCopyBtn()
        }
        checkNotification(NOTIFICATION_CAPTION)
        closeInspectorWindow()
    }

    @Test
    @DisplayName("Checks copying of Update script from Entity Inspector Window")
    void checkAndCopyUpdateScript() {
        commonInspectorActions()

        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)

            checkAndClickUpdateBtn(UPDATE_BTN_CAPTION)
            checkScriptArea(SCRIPT_AREA, "update")
            checkAndClickCopyBtn()
        }
        checkNotification(NOTIFICATION_CAPTION)
        closeInspectorWindow()
    }
}
