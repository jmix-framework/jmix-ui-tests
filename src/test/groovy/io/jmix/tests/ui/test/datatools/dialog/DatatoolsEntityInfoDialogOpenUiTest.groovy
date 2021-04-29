package io.jmix.tests.ui.test.datatools.dialog

import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.dialogs.EntityInformationDialog
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

class DatatoolsEntityInfoDialogOpenUiTest extends BaseDatatoolsUiTests {

    @Test
    @DisplayName("Open Entity information from deleted entity in Entity Inspector Browser")
    void openContextMenuFromDeletedEntity() {
        loginAsAdmin()
        createUser(USERNAME1)
        $j(UserBrowse).removeUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(REMOVED_ONLY_MODE)
            selectRowInTableByText(USERNAME1, USER_TABLE_JTEST_ID)
        }

        openInspectorWindow(4)

        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)
        }

        closeInspectorWindow()
        wipeOutData(USER_ENTITY_NAME, USER_FULL_STRING, ALL_MODE, USER_TABLE_JTEST_ID, USERNAME1)
    }

    @Test
    @DisplayName("Open Entity information from non-deleted entity in Entity Inspector Browser")
    void openContextMenuFromNonDeletedEntity() {
        loginAsAdmin()
        createUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectRowInTableByText(USERNAME1, USER_TABLE_JTEST_ID)
        }

        openInspectorWindow(4)
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)
        }
        closeInspectorWindow()
        wipeOutData(USER_ENTITY_NAME, USER_FULL_STRING, ALL_MODE, USER_TABLE_JTEST_ID, USERNAME1)
    }
}
