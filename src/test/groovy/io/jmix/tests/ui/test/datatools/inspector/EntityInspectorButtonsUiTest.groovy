package io.jmix.tests.ui.test.datatools.inspector

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.reports.dialog.ExcelExportModeOptionDialog
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.OptionDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,datatools'])
@ContextConfiguration(initializers = TestContextInitializer)
class EntityInspectorButtonsUiTest extends BaseDatatoolsUiTest {

    @Test
    @DisplayName("Deletes User from Entity Inspector Browser")
    void deleteUserFromInspector() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(NON_REMOVED_ONLY_MODE)
            selectRowInTableByText(USERNAME3, USER_TABLE_JTEST_ID)
            clickButton(remove)
        }
        $j(ConfirmationDialog).confirmChanges()

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME3, USER_TABLE_JTEST_ID)

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsNotDisplayed(USERNAME3)
    }

    @Test
    @DisplayName("Restores User from Entity Inspector Browser")
    void restoreUserFromInspector() {
        loginAsAdmin()
        $j(MainScreen).openUserBrowse()

        $j(UserBrowse).removeUser(USERNAME4)
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(ALL_MODE)
            selectRowInTableByText(USERNAME4, USER_TABLE_JTEST_ID)
            clickButton(restore)
        }
        $j(OptionDialog).confirm()

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsDisplayed(USERNAME4)
    }

    @Test
    @DisplayName("Wipes out User from Entity Inspector Browser")
    void wipeOutUserFromInspector() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(ALL_MODE)
            selectRowInTableByText(USERNAME5, USER_TABLE_JTEST_ID)
            clickButton(wipeOut)
        }
        $j(OptionDialog).confirm()

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME5, USER_TABLE_JTEST_ID)
        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).checkRecordIsNotDisplayed(USERNAME5)
    }

    @Test
    @DisplayName("Exports User instance using JSON and ZIP formats from Entity Inspector Browser")
    void exportUserFromInspector() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            exportFromInspectorWindow("exportJSON", USER_TABLE_JTEST_ID)
            exportFromInspectorWindow("exportZIP", USER_TABLE_JTEST_ID)
        }
    }

    @Test
    @DisplayName("Exports selected User instance using Excel export from Entity Inspector Browser")
    void excelExportUserFromInspector() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectRowInTableByText(USERNAME2, USER_TABLE_JTEST_ID)
            clickButton(excelExport)
        }
        $j(ExcelExportModeOptionDialog).with {
            clickButton(selectedRows)
        }
    }

    @Test
    @DisplayName("Exports all User instances using Excel export from Entity Inspector Browser")
    void excelExportAllUsersFromInspector() {
        loginAsAdmin()

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            clickButton(excelExport)
        }
        $j(ExcelExportModeOptionDialog).with {
            clickButton(allRows)
        }
    }
}
