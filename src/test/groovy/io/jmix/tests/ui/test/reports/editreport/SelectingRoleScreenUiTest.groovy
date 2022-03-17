package io.jmix.tests.ui.test.reports.editreport

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.system.dialog.UnsavedChangesDialog
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
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
        properties = ['jmix.liquibase.contexts=base,reports'])
@ContextConfiguration(initializers = TestContextInitializer)
class SelectingRoleScreenUiTest extends BaseReportUiTest {

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).openReportsBrowse()
        openNewReportEditor()

        $j(ReportEditor).with {
            openTab(SECURITY_TAB_JTEST_ID)
        }
    }

    @AfterEach
    void afterEach() {
        $j(ReportEditor).with {
            clickButton(cancel)
        }
        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    @Test
    @DisplayName("Selects role for report")
    void selectRoleForReport() {
        $j(ReportEditor).with {
            selectValueWithoutFilterInComboBox($j(ComboBox, COMBOBOX_ROLES_JTEST_ID), REPORT_ROLE_NAME)
            clickButton($j(Button, ROLE_ADD_BUTTON_JTEST_ID))

            checkRecordIsDisplayed(REPORT_ROLE_NAME, REPORTS_ROLES_JTEST_ID)
        }
    }

    @Test
    @DisplayName("Selects screen for report")
    void selectScreenForReport() {
        $j(ReportEditor).with {
            selectValueWithoutFilterInComboBox($j(ComboBox, COMBOBOX_SCREENS_JTEST_ID), REPORT_SCREEN_NAME)
            clickButton($j(Button, SCREEN_ADD_BUTTON_JTEST_ID))

            checkRecordIsDisplayed(REPORT_SCREEN_NAME, REPORTS_SCREENS_JTEST_ID)
        }
    }
}
