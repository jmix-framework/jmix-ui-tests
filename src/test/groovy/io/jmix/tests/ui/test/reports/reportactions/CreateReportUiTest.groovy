package io.jmix.tests.ui.test.reports.reportactions

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.dialog.InputParametersDialog
import io.jmix.tests.ui.screen.reports.dialog.JPQLQueryEditorDialog
import io.jmix.tests.ui.screen.reports.dialog.ReportRegionsDialog
import io.jmix.tests.ui.screen.reports.editor.ReportTemplateEditor
import io.jmix.tests.ui.screen.reports.dialog.SaveReportDialog
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Conditions.*

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,reports'])
@ContextConfiguration(initializers = TestContextInitializer)
class CreateReportUiTest extends BaseReportUiTest {

    public static final String EDIT_BUTTON_JTEST_ID = "edit"
    public static final String COMBOBOX_OUTPUT_TYPE_JTEST_ID = "outputTypeComboBox"

    @Test
    @DisplayName("Creates a report for one entity using wizard")
    void createReportForOneEntity() {
        loginAsAdmin()
        maximizeWindowSize()

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)
        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
    }

    @Test
    @DisplayName("Creates a report for one entity using Copy button")
    void createReportByCopying() {
        loginAsAdmin()
        maximizeWindowSize()
        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)
        createBasicReportForCompanyEntity(reportName)

        def reportCopyName = reportName + " (2)"
        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(copy)
            checkRecordIsDisplayed(reportCopyName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
        removeReport(reportCopyName)
    }

    @Test
    @DisplayName("Creates a report for entities list using wizard")
    void createReportForEntitiesList() {
        loginAsAdmin()
        maximizeWindowSize()

        openReportCreationWizard()
        selectReportType(REPORT_TYPE_LIST_ENTITIES)
        chooseReportEntity(COMPANY_FULL_STRING, COMPANY_ENTITY_NAME)

        def list = [COMPANY_TYPE, COMPANY_EMAIL, COMPANY_GRADE, COMPANY_REGISTRATION_ID]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(nextBtn)
        }

        $j(SaveReportDialog).with {
            save()
        }

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        $j(ReportEditor).with {
            name.setValue(reportName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
    }

    @Test
    @DisplayName("Creates a report for entities list by query using wizard")
    void createReportForEntitiesListByQuery() {
        loginAsAdmin()
        maximizeWindowSize()

        openReportCreationWizard()
        selectReportType(REPORT_TYPE_LIST_ENTITIES_QUERY)
        chooseReportEntity(COMPANY_FULL_STRING, COMPANY_ENTITY_NAME)

        def list = [COMPANY_TYPE, COMPANY_EMAIL, COMPANY_GRADE, COMPANY_REGISTRATION_ID]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(nextBtn)
        }

        $j(JPQLQueryEditorDialog).with {
            clickButton(nextBtn)
        }

        $j(SaveReportDialog).with {
            save()
        }

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        $j(ReportEditor).with {
            name.setValue(reportName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
    }

    @Test
    @DisplayName("Creates a report for entities list using table type")
    void createReportUsingTableType() {
        loginAsAdmin()
        maximizeWindowSize()

        openReportCreationWizard()
        selectReportType(REPORT_TYPE_LIST_ENTITIES)
        chooseReportTemplateType(TABLE_TYPE)
        chooseReportEntity(COMPANY_FULL_STRING, COMPANY_ENTITY_NAME)
        def list = [COMPANY_TYPE, COMPANY_EMAIL, COMPANY_GRADE, COMPANY_REGISTRATION_ID]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(nextBtn)
        }

        $j(SaveReportDialog).with {
            save()
        }

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        $j(ReportEditor).with {
            name.setValue(reportName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
    }

    @Test
    @DisplayName("Creates a report for entities list using serial chart type")
    void createReportUsingSerialChartType() {
        loginAsAdmin()
        maximizeWindowSize()

        openReportCreationWizard()
        selectReportType(REPORT_TYPE_LIST_ENTITIES)
        chooseReportTemplateType(CHART_TYPE)
        chooseReportEntity(ATMOSPHERE_FULL_STRING, ATMOSPHERE_ENTITY_NAME)
        def list = [ATMOSPHERE_DESCRIPTION, ATMOSPHERE_PRESSURE]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(nextBtn)
        }

        $j(SaveReportDialog).with {
            $j(ComboBox, COMBOBOX_DIAGRAM_TYPE_JTEST_ID)
                    .openOptionsPopup()
                    .select(SERIAL_TYPE)
            save()
        }

        def reportName = getReportUniqueName(ATMOSPHERE_ENTITY_NAME)

        $j(ReportEditor).with {
            name.setValue(reportName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
    }

    @Test
    @DisplayName("Creates a report for entities list using pie chart type")
    void createReportUsingPieChartType() {
        loginAsAdmin()
        maximizeWindowSize()

        openReportCreationWizard()
        selectReportType(REPORT_TYPE_LIST_ENTITIES)
        chooseReportTemplateType(CHART_TYPE)
        chooseReportEntity(ATMOSPHERE_FULL_STRING, ATMOSPHERE_ENTITY_NAME)
        def list = [ATMOSPHERE_DESCRIPTION, ATMOSPHERE_PRESSURE]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(nextBtn)
        }

        $j(SaveReportDialog).with {
            $j(ComboBox, COMBOBOX_DIAGRAM_TYPE_JTEST_ID)
                    .openOptionsPopup()
                    .select(PIE_TYPE)
            save()
        }

        def reportName = getReportUniqueName(ATMOSPHERE_ENTITY_NAME)

        $j(ReportEditor).with {
            name.setValue(reportName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
    }

    @Test
    @DisplayName("Creates report with alterable output")
    void createReportWithAlterableOutput() {
        loginAsAdmin()
        maximizeWindowSize()

        openReportCreationWizard()
        chooseReportEntity(COMPANY_FULL_STRING, COMPANY_ENTITY_NAME)

        def list = [COMPANY_TYPE, COMPANY_EMAIL, COMPANY_GRADE]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(nextBtn)
        }

        $j(SaveReportDialog).with {
            save()
        }

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        $j(ReportEditor).with {
            name.setValue(reportName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            openTab(TEMPLATES_TAB_JTEST_ID)
            def templateName = "Template for report \"" + getReportBasicName(COMPANY_ENTITY_NAME) + "\".docx"
            checkRecordIsDisplayed(templateName, TEMPLATES_TABLE_JTEST_ID)
            selectRowInTableByText(templateName, TEMPLATES_TABLE_JTEST_ID)
            clickButton($j(Button, EDIT_BUTTON_JTEST_ID))
        }

        $j(ReportTemplateEditor).with {
            alterableField.shouldBe(VISIBLE).setChecked(true)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(run)
        }

        $j(InputParametersDialog).with {
            $j(ComboBox, COMBOBOX_OUTPUT_TYPE_JTEST_ID)
                    .shouldBe(VISIBLE)
                    .shouldBe(ENABLED)
            clickButton(cancelButton)
        }

        removeReport(reportName)
    }

}
