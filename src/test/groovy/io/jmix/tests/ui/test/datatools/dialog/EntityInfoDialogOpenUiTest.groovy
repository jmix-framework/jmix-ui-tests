package io.jmix.tests.ui.test.datatools.dialog

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.dialogs.EntityInformationDialog
import io.jmix.tests.ui.screen.application.user.UserBrowse
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
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class EntityInfoDialogOpenUiTest extends BaseDatatoolsUiTest {

    @Test
    @DisplayName("Opens Entity information from deleted entity in Entity Inspector Browser")
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
    @DisplayName("Opens Entity information from non-deleted entity in Entity Inspector Browser")
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

    @Test
    @DisplayName("Opens Entity information from non-deleted entity in User browser")
    void openContextMenuFromUserBrowser() {
        loginAsAdmin()

        $j(MainScreen).openUserBrowse()

        $j(UserBrowse).with {
            selectRowInTableByText(ADMIN_USERNAME, USER_BROWSER_TABLE_JTEST_ID)
        }

        openInspectorWindow(6)
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)
        }
        closeInspectorWindow()
    }
}
