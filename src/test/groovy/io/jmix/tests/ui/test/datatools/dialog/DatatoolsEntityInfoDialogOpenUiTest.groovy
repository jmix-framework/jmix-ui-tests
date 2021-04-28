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

class DatatoolsEntityInfoDialogOpenUiTest extends BaseDatatoolsUiTests{
    @Test
    @DisplayName("Open Entity information from deleted entity in Entity Inspector Browser")
    void openContextMenuFromDeletedEntity() {
        loginAsAdmin()
        createUser(USERNAME1)
        $j(UserBrowse).removeUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(ENTITY_NAME,USER)
            selectShowMode(REMOVED_ONLY_MODE)
            selectRowInUserTableByUsername(USERNAME1)
        }

        openInspectorWindow(4)

        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(ENTITY_NAME)
        }

        closeInspectorWindow()
    }

    @Test
    @DisplayName("Open Entity information from non-deleted entity in Entity Inspector Browser")
    void openContextMenuFromNonDeletedEntity() {
        loginAsAdmin()
        createUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(ENTITY_NAME,USER)
            selectRowInUserTableByUsername(USERNAME1)
        }

        openInspectorWindow(4)
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(ENTITY_NAME)
        }
        closeInspectorWindow()
    }
}
