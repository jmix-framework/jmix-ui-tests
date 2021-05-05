package io.jmix.tests.ui.test.datatools.inspector

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.OptionDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTests
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
class DatatoolsEntityInspectorButtonsUiTest extends BaseDatatoolsUiTests {

    @Test
    @DisplayName("Delete User from Entity Inspector Browser")
    void deleteUserFromInspector() {
        loginAsAdmin()

        createUser(USERNAME1)
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(NON_REMOVED_ONLY_MODE)
            selectRowInTableByText(USERNAME1, USER_TABLE_JTEST_ID)
            clickButton(remove)
        }
        $j(ConfirmationDialog).confirm()

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME1, USER_TABLE_JTEST_ID)

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsNotDisplayed(USERNAME1)

        wipeOutData(USER_ENTITY_NAME, USER_FULL_STRING, ALL_MODE, USER_TABLE_JTEST_ID, USERNAME1)
    }

    @Test
    @DisplayName("Restore User from Entity Inspector Browser")
    void restoreUserFromInspector() {
        loginAsAdmin()

        createUser(USERNAME1)
        $j(UserBrowse).removeUser(USERNAME1)
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(ALL_MODE)
            selectRowInTableByText(USERNAME1, USER_TABLE_JTEST_ID)
            clickButton(restore)
        }
        $j(OptionDialog).confirm()

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsDisplayed(USERNAME1)

        wipeOutData(USER_ENTITY_NAME, USER_FULL_STRING, ALL_MODE, USER_TABLE_JTEST_ID, USERNAME1)

    }

    @Test
    @DisplayName("Wipe out User from Entity Inspector Browser")
    void wipeOutUserFromInspector() {
        loginAsAdmin()
        createUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(ALL_MODE)
            selectRowInTableByText(USERNAME1, USER_TABLE_JTEST_ID)
            clickButton(wipeOut)
        }
        $j(OptionDialog).confirm()

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME1, USER_TABLE_JTEST_ID)
        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsNotDisplayed(USERNAME1)
    }

    @Test
    @DisplayName("Export User instance using JSON and ZIP formats from Entity Inspector Browser")
    void exportUserFromInspector() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            exportFromInspectorWindow("exportJSON",USER_TABLE_JTEST_ID)
            exportFromInspectorWindow("exportZIP",USER_TABLE_JTEST_ID)
        }

    }

}
