package io.jmix.tests.ui.test.reports.editreport

import io.jmix.masquerade.component.Button
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.system.dialog.UnsavedChangesDialog
import io.jmix.tests.ui.screen.reports.editor.ValueFormatEditor
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.test.reports.BaseReportUiTest
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
class ReportFormatValueActionUiTest extends BaseReportUiTest {

    @Test
    @DisplayName("Creates format value")
    void createFormatValue() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
            clickButton($j(Button, VALUE_CREATE_BUTTON_JTEST_ID))
        }

        $j(ValueFormatEditor).with {
            valueNameField.setValue(COMPANY_NAME)
            formatField.openOptionsPopup().select(FORMAT_OPTION_HTML)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    @Test
    @DisplayName("Edits format value")
    void editFormatValue() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
            clickButton($j(Button, VALUE_CREATE_BUTTON_JTEST_ID))
        }

        $j(ValueFormatEditor).with {
            valueNameField.setValue(COMPANY_NAME)
            formatField.openOptionsPopup().select(FORMAT_OPTION_HTML)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
            selectRowInTableByText(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
            clickButton($j(Button, VALUE_EDIT_BUTTON_JTEST_ID))
        }

        $j(ValueFormatEditor).with {
            valueNameField.setValue(COMPANY_GRADE)
            formatField.openOptionsPopup().select(FORMAT_OPTION_HTML)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_GRADE, VALUES_FORMATS_TABLE_JTEST_ID)
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    @Test
    @DisplayName("Removes format value")
    void removeFormatValue() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
            clickButton($j(Button, VALUE_CREATE_BUTTON_JTEST_ID))
        }

        $j(ValueFormatEditor).with {
            valueNameField.setValue(COMPANY_NAME)
            formatField.openOptionsPopup().select(FORMAT_OPTION_HTML)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
            selectRowInTableByText(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
            clickButton($j(Button, VALUE_REMOVE_BUTTON_JTEST_ID))
        }

        $j(ConfirmationDialog).with {
            clickButton(yes)
        }

        $j(ReportEditor).with {
            checkRecordIsNotDisplayed(COMPANY_GRADE, VALUES_FORMATS_TABLE_JTEST_ID)
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }
}
