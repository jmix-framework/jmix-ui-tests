package io.jmix.tests.ui.test.datatools.dialog

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
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
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,datatools'])
@ContextConfiguration(initializers = TestContextInitializer)
class EntityInfoDialogOpenUiTest extends BaseDatatoolsUiTest {

    @Test
    @DisplayName("Opens Entity information from deleted entity in Entity Inspector Browser")
    void openContextMenuFromDeletedEntity() {
        loginAsAdmin()

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).removeUser(USERNAME6)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(REMOVED_ONLY_MODE)
            openContextMenuFromSelectedRow(USERNAME6, USER_TABLE_JTEST_ID)
        }

        openInspectorWindow(5)

        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)
        }

        closeInspectorWindow()
        wipeOutData(USER_ENTITY_NAME, USER_FULL_STRING, ALL_MODE, USER_TABLE_JTEST_ID, USERNAME6)
    }

    @Test
    @DisplayName("Opens Entity information from non-deleted entity in Entity Inspector Browser")
    void openContextMenuFromNonDeletedEntity() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            openContextMenuFromSelectedRow(USERNAME2, USER_TABLE_JTEST_ID)
        }

        openInspectorWindow(5)
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)
        }
        closeInspectorWindow()
    }

    @Test
    @DisplayName("Opens Entity information from non-deleted entity in User browser")
    void openContextMenuFromUserBrowser() {
        loginAsAdmin()

        $j(MainScreen).openUserBrowse()

        $j(UserBrowse).with {
            openContextMenuFromSelectedRow(ADMIN_USERNAME, USER_BROWSER_TABLE_JTEST_ID)
        }

        openInspectorWindow(6)
        $j(EntityInformationDialog).with {
            shouldBe(VISIBLE)
                    .checkTableAndEntityName(USER_ENTITY_NAME)
        }
        closeInspectorWindow()
    }
}
