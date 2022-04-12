package io.jmix.tests.ui.test.reports.reportactions

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.dialog.InputParametersDialog
import io.jmix.tests.ui.screen.reports.dialog.JPQLQueryEditorDialog
import io.jmix.tests.ui.screen.reports.dialog.ReportRegionsDialog
import io.jmix.tests.ui.screen.reports.editor.ReportParameterEditor
import io.jmix.tests.ui.screen.reports.editor.ReportTemplateEditor
import io.jmix.tests.ui.screen.reports.dialog.SaveReportDialog
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId

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
    public static final String TEMPLATE_BASIC_FILE_NAME = "template.docx"
    public static final String ENTITY_FIELD_J_TEST_ID = "metaClassField"
    public static final String ENTITY_SCREEN_FIELD_J_TEST_ID = "screenField"
    public static final String SCREEN_VALUE = "Company browser (Company.browse)"
    public static final String PARAMETERS_AND_FORMATS_TAB_J_TEST_ID = "parametersAndFormatsTab"
    public static final String ENTITY_LOW_CASE_STR = "entity"
    public static final String ENTITY_CAPITALIZED_STRING = "Entity"
    public static final String GENERAL_TAB_J_TEST_ID = "generalTab"
    public static final String DATASET_TYPE_COMBOBOX_J_TEST_ID = "type"
    public static final String BAND_DETAILS_BOX_J_TEST_ID = "bandDetailsBox"
    public static final String ENTITY_PARAM_NAME_COMBOBOX_J_TEST_ID = "entityParamField"
    public static final String NEW_BAND_J_TEST_ID = "newBand1"
    public static final String DATASET_NAME_FIELD_J_TEST_ID = "name"
    public static final String TEMPLATE_FILE_NAME = "template.docx"

    static void openReportParameterEditor() {
        $j(ReportEditor).with {
            clickButton($j(Button, PARAMETER_CREATE_BUTTON_JTEST_ID))
        }
    }

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).openReportsBrowse()
    }

    @Test
    @Disabled
    @DisplayName("Creates a report for one entity without using wizard")
    void createReportForOneEntityWithoutWizard() {
        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        def templateFileName = getGeneratedString() + TEMPLATE_FILE_NAME
        def templateFilePath = RESOURCES_PATH + TEMPLATE_BASIC_FILE_NAME
        def templateUniqueFilePath = RESOURCES_PATH + templateFileName
        def templateFile = createNewFile(templateFilePath, templateUniqueFilePath)

        openNewReportEditor()
        $j(ReportEditor).with {
            fillTextField(name, reportName)
            clickButton(createTemplate)
        }

        $j(ReportTemplateEditor).with {
            uploadNewDocument(templateUploadField, templateFile)
            checkUploadedFilename(templateFileName)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_TAB_J_TEST_ID)
            openReportParameterEditor()
        }

        $j(ReportParameterEditor).with {
            name.setValue(ENTITY_LOW_CASE_STR)
            alias.setValue(ENTITY_LOW_CASE_STR)
            selectValueWithoutFilterInComboBox(parameterTypeField, ENTITY_CAPITALIZED_STRING)
            selectValueWithoutFilterInComboBox($j(ComboBox, ENTITY_FIELD_J_TEST_ID), COMPANY_FULL_STRING)
            selectValueWithoutFilterInComboBox($j(ComboBox, ENTITY_SCREEN_FIELD_J_TEST_ID), SCREEN_VALUE)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(ENTITY_LOW_CASE_STR, PARAMETERS_TABLE_JTEST_ID)
        }

        $j(ReportEditor).with {
            openTab(GENERAL_TAB_J_TEST_ID)
            fillTextField(name, reportName)
            clickButton(createReportBand)
            clickBandsTreeNode(NEW_BAND_J_TEST_ID)
            clickButton(createBand)
            selectValueWithoutFilterInComboBox($j(ComboBox, DATASET_TYPE_COMBOBOX_J_TEST_ID), ENTITY_CAPITALIZED_STRING)
            fillTextField($j(TextField, byChain(byJTestId(BAND_DETAILS_BOX_J_TEST_ID), byJTestId(DATASET_NAME_FIELD_J_TEST_ID))), "Set")
            selectValueWithoutFilterInComboBox($j(ComboBox, ENTITY_PARAM_NAME_COMBOBOX_J_TEST_ID), ENTITY_LOW_CASE_STR)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
        removeReport(reportName)
        cleanTempFile(templateUniqueFilePath)
    }

    @Test
    @DisplayName("Creates a report for one entity using wizard")
    void createReportForOneEntity() {
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
            selectValueWithoutFilterInComboBox($j(ComboBox, COMBOBOX_DIAGRAM_TYPE_JTEST_ID), SERIAL_TYPE)
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
            selectValueWithoutFilterInComboBox($j(ComboBox, COMBOBOX_DIAGRAM_TYPE_JTEST_ID), PIE_TYPE)
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
