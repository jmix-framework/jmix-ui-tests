package io.jmix.tests.ui.test.datatools.dialog

import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.dialogs.EntityInformationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

class DatatoolsEntityInfoDialogButtonsUiTest extends BaseDatatoolsUiTests {
    String INSERT_BTN_CAPTION = "Script for insert"
    String SELECT_BTN_CAPTION = "Script for select"
    String UPDATE_BTN_CAPTION = "Script for update"
    String NOTIFICATION_CAPTION = "Copied to clipboard"
    String SCRIPT_AREA = "scriptArea"

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
    @DisplayName("Check copying of Insert script from Entity Inspector Window")
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
    @DisplayName("Check copying of Select script from Entity Inspector Window")
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
    @DisplayName("Check copying of Update script from Entity Inspector Window")
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
