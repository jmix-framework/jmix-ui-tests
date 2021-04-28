package io.jmix.tests.ui.test.datatools.inspector

import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.dialogs.OptionDialog
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static io.jmix.masquerade.Selectors.$j


class DatatoolsEntityInspectorButtonsUiTest extends BaseDatatoolsUiTests {

    @Test
    @DisplayName("Delete User from Entity Inspector Browser")
    void deleteUserFromInspector() {
        loginAsAdmin()

        createUser(USERNAME1)
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(ENTITY_NAME,USER)
            selectShowMode(NON_REMOVED_ONLY_MODE)
            selectRowInUserTableByUsername(USERNAME1)
            clickRemoveButton()
        }
        $j(ConfirmationDialog).confirm()

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME1)

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsNotDisplayed(USERNAME1)
    }

    @Test
    @DisplayName("Restore User from Entity Inspector Browser")
    void restoreUserFromInspector() {
        loginAsAdmin()

        createUser(USERNAME1)
        $j(UserBrowse).removeUser(USERNAME1)
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(ENTITY_NAME,USER)
            selectShowMode(ALL_MODE)
            selectRowInUserTableByUsername(USERNAME1)
            clickRestoreButton()
        }
        $j(OptionDialog).confirm()

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsDisplayed(USERNAME1)

    }

    @Test
    @DisplayName("Wipe out User from Entity Inspector Browser")
    void wipeOutUserFromInspector() {
        loginAsAdmin()
        createUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(ENTITY_NAME,USER)
            selectShowMode(ALL_MODE)
            selectRowInUserTableByUsername(USERNAME1)
            clickWipeOutButton()
        }
        $j(OptionDialog).confirm()

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME1)
        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsNotDisplayed(USERNAME1)
    }


}
