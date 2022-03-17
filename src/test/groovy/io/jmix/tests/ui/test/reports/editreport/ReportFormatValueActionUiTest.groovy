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
class ReportFormatValueActionUiTest extends BaseReportUiTest {

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).openReportsBrowse()
        openNewReportEditor()
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

    static void openValueFormatEditor() {
        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
            clickButton($j(Button, VALUE_CREATE_BUTTON_JTEST_ID))
        }
    }

    static void setNameAndHTMLFormatToValue(String name) {
        $j(ValueFormatEditor).with {
            valueNameField.setValue(name)
            selectValueWithoutFilterInComboBox(formatField, FORMAT_OPTION_HTML)
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Creates format value")
    void createFormatValue() {
        openValueFormatEditor()
        setNameAndHTMLFormatToValue(COMPANY_NAME)

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
        }
    }

    @Test
    @DisplayName("Edits format value")
    void editFormatValue() {
        openValueFormatEditor()
        setNameAndHTMLFormatToValue(COMPANY_NAME)

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
            selectRowInTableByText(COMPANY_NAME, VALUES_FORMATS_TABLE_JTEST_ID)
            clickButton($j(Button, VALUE_EDIT_BUTTON_JTEST_ID))
        }
        setNameAndHTMLFormatToValue(COMPANY_GRADE)

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_GRADE, VALUES_FORMATS_TABLE_JTEST_ID)
        }
    }

    @Test
    @DisplayName("Removes format value")
    void removeFormatValue() {
        openValueFormatEditor()
        setNameAndHTMLFormatToValue(COMPANY_NAME)

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
        }
    }
}
