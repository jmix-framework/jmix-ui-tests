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
import org.junit.jupiter.api.BeforeEach
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

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            openContextMenuFromSelectedRow(ADMIN_USERNAME, USER_TABLE_JTEST_ID)
        }

        openInspectorWindow(4)
    }

    @Test
    @DisplayName("Checks copying of Insert script from Entity Inspector Window")
    void checkAndCopyInsertScript() {
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)

            checkBtnCaptionAndClick(insertBtn, INSERT_BTN_CAPTION)
            checkScriptArea(SCRIPT_AREA, "insert")
            clickButton(copyBtn)
        }
        checkNotification(NOTIFICATION_CAPTION)
        closeInspectorWindow()
    }

    @Test
    @DisplayName("Checks copying of Select script from Entity Inspector Window")
    void checkAndCopySelectScript() {
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)

            checkBtnCaptionAndClick(selectBtn, SELECT_BTN_CAPTION)
            checkScriptArea(SCRIPT_AREA, "select")
            clickButton(copyBtn)
        }
        checkNotification(NOTIFICATION_CAPTION)
        closeInspectorWindow()
    }

    @Test
    @DisplayName("Checks copying of Update script from Entity Inspector Window")
    void checkAndCopyUpdateScript() {
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)

            checkBtnCaptionAndClick(updateBtn, UPDATE_BTN_CAPTION)
            checkScriptArea(SCRIPT_AREA, "update")
            clickButton(copyBtn)
        }
        checkNotification(NOTIFICATION_CAPTION)
        closeInspectorWindow()
    }
}
