package io.jmix.tests.ui.test.datatools.inspector

import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import static io.jmix.masquerade.Selectors.$j

class DatatoolsEntityInspectorShowModesUiTest extends BaseDatatoolsUiTests {

    @Test
    @DisplayName("Check displaying deleted User in Entity Inspector Browser in different modes")
    void checkDifferentModes() {
        loginAsAdmin()

        createUser(USERNAME1)
        createUser(USERNAME2)
        $j(UserBrowse).removeUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(NON_REMOVED_ONLY_MODE)
        }

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME1, USER_TABLE_JTEST_ID)
        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME2, USER_TABLE_JTEST_ID)

        $j(EntityInspectorBrowse).selectShowMode(REMOVED_ONLY_MODE)

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME2, USER_TABLE_JTEST_ID)
        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME1, USER_TABLE_JTEST_ID)

        $j(EntityInspectorBrowse).selectShowMode(ALL_MODE)

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME2, USER_TABLE_JTEST_ID)
        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME1, USER_TABLE_JTEST_ID)

        wipeOutData(USER_ENTITY_NAME, USER_FULL_STRING, ALL_MODE, USER_TABLE_JTEST_ID, USERNAME1)
        wipeOutData(USER_ENTITY_NAME, USER_FULL_STRING, ALL_MODE, USER_TABLE_JTEST_ID, USERNAME2)

    }
}
