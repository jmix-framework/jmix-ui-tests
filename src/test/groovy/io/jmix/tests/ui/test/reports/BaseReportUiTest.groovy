package io.jmix.tests.ui.test.reports

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide
import io.jmix.masquerade.component.Button
import io.jmix.tests.ui.screen.application.company.CompanyBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportGroupBrowse
import io.jmix.tests.ui.screen.reports.dialog.*
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.test.BaseUiTest
import io.jmix.tests.ui.test.utils.UiHelper

import static com.codeborne.selenide.Selectors.withText
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static io.jmix.masquerade.Conditions.DISABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byClassName

abstract class BaseReportUiTest extends BaseUiTest implements UiHelper {
    public static final String COMPANY_FULL_STRING = "Company (Company)"
    public static final String ATMOSPHERE_FULL_STRING = "Atmosphere (Atmosphere)"

    public static final String COMPANY_ENTITY_NAME = "Company"
    public static final String GAS_ENTITY_NAME = "Gas"
    public static final String ATMOSPHERE_ENTITY_NAME = "Atmosphere"

    public static final String USING_WIZARD_MODE = "Using wizard"
    public static final String NEW_MODE = "New"

    public static final String FORMAT_OPTION_HTML = "\${html}"

    public static final String COMPANY_TYPE = "Company type"
    public static final String COMPANY_EMAIL = "Email"
    public static final String COMPANY_GRADE = "Grade"
    public static final String COMPANY_REGISTRATION_ID = "Registration id"
    public static final String COMPANY_NAME = "Name"

    public static final String ATMOSPHERE_DESCRIPTION = "Description"
    public static final String ATMOSPHERE_PRESSURE = "Pressure"
    public static final String ATMOSPHERE_GASES = "Gases"
    public static final String ATMOSPHERIC_GAS_VOLUME = "Volume"

    public static final String GAS_NAME = "Name"

    public static final String EMPTY_STRING = ""
    public static final String TABLE_TYPE = "Table"
    public static final String CHART_TYPE = "Chart"
    public static final String SERIAL_TYPE = "Serial chart"
    public static final String PIE_TYPE = "Pie chart"

    public static final String REPORT_TABLE_EXPAND_SELECTOR = "v-table-cell-wrapper"
    public static final String REPORT_ROLE_NAME = "Reports: full access"
    public static final String REPORT_SCREEN_NAME = "Company.browse"

    public static final String SYSTEM_GROUP_REMOVING_NOTIFICATION = "Unable to delete system group of reports"
    public static final String NON_EMPTY_GROUP_REMOVING_NOTIFICATION = "Unable to delete group with reports"

    public static final String COMPANY_TEST_NAME = "Test company"
    public static final String COMPANY_TEST_EMAIL = "test@test.ru"

    public static final String GROUP_TEST_NAME = "Test group"
    public static final String GROUP_TEST_CODE = "testgroup"
    public static final String GROUP_GENERAL_NAME = "General"

    public static final String COMPANY_TABLE_JTEST_ID = "companiesTable"
    public static final String REGIONS_TABLE_JTEST_ID = "regionsTable"
    public static final String REPORTS_TABLE_JTEST_ID = "reportsTable"
    public static final String REPORTS_ROLES_JTEST_ID = "rolesTable"
    public static final String REPORTS_SCREENS_JTEST_ID = "screenTable"
    public static final String REPORTS_PROPERTIES_TABLE_JTEST_ID = "propertiesTable"
    public static final String REPORTS_GROUPS_TABLE_JTEST_ID = "reportGroupsTable"
    public static final String REPORTS_EXECUTIONS_TABLE_JTEST_ID = "executionsTable"
    public static final String VALUES_FORMATS_TABLE_JTEST_ID = "valuesFormatsTable"
    public static final String PARAMETERS_TABLE_JTEST_ID = "inputParametersTable"
    public static final String COMPANY_SHOW_TABLES_JTEST_ID = "CompanysTable"
    public static final String TEMPLATES_TABLE_JTEST_ID = "templatesTable"

    public static final String COMBOBOX_DIAGRAM_TYPE_JTEST_ID = "diagramType"
    public static final String COMBOBOX_ROLES_JTEST_ID = "rolesField"
    public static final String COMBOBOX_SCREENS_JTEST_ID = "screenIdField"
    public static final String COMBOBOX_DATASETS_TYPE_JTEST_ID = "type"
    public static final String COMBOBOX_DATASTORES_JTEST_ID = "dataStoreField"
    public static final String VALUE_CREATE_BUTTON_JTEST_ID = "createValueFormat"
    public static final String VALUE_EDIT_BUTTON_JTEST_ID = "editValueFormat"
    public static final String VALUE_REMOVE_BUTTON_JTEST_ID = "removeValueFormat"
    public static final String PARAMETER_CREATE_BUTTON_JTEST_ID = "createParameter"
    public static final String PARAMETER_EDIT_BUTTON_JTEST_ID = "editParameter"
    public static final String PARAMETER_REMOVE_BUTTON_JTEST_ID = "removeParameter"
    public static final String FETCH_PLAN_EDIT_BUTTON_JTEST_ID = "fetchPlanEditButton"

    public static final String ROLE_ADD_BUTTON_JTEST_ID = "addRoleBtn"
    public static final String OK_BUTTON_JTEST_ID = "lookupSelectAction"
    public static final String OPTION_DIALOG_OK_BUTTON_JTEST_ID = "optionDialog_ok"
    public static final String SCREEN_ADD_BUTTON_JTEST_ID = "addReportScreenBtn"
    public static final String SECURITY_TAB_JTEST_ID = "securityTab"
    public static final String TEMPLATES_TAB_JTEST_ID = "templatesTab"
    public static final String PARAMETERS_AND_FORMATS_JTEST_ID = "parametersAndFormatsTab"

    public static final String ADD_REGION_POPUP_JTEST_ID = "addRegionPopupBtn"
    public static final String ADD_REGION_POPUP_NAME = "Add tabulated region"

    public static final String REPORT_TYPE_LIST_ENTITIES = "Report for list of entities"
    public static final String REPORT_TYPE_LIST_ENTITIES_QUERY = "Report for list of entities, selected by query"

    public static final String RESOURCES_PATH = "src/main/resources/"
    public static final String IMPORTED_REPORT_NAME = "Report for entity \"Company\""
    public static final String REPORT_FILE_NAME = "report.zip"

    static String getReportBasicName(String entityName) {
        return "Report for entity \"" + entityName + "\""
    }

    static String getReportUniqueName(String entityName) {
        def generatedString = getGeneratedString()
        return "Report for entity \"" + entityName + "\"" + generatedString
    }

    static String getGroupUniqueName(String groupName) {
        def generatedString = getGeneratedString()
        return groupName + generatedString
    }

    static String getString(ArrayList<String> strings) {
        StringBuilder builder = new StringBuilder(strings.get(0))
        for (int i = 1; i < strings.size(); i++) {
            builder.append(", ")
            builder.append(strings.get(i))
        }
        return builder.toString()
    }

    static List<String> getConcatenatedList(List<String> list, String str) {
        def res = []
        for (String e : list) {
            res.add(str.concat(e))
        }
        return res
    }

    static void openReportCreationWizard() {
        $j(ReportBrowse).with {
            chooseCreatingTypeInCreatePopupButton(USING_WIZARD_MODE)
        }
    }

    static void openNewReportEditor() {
        $j(ReportBrowse).with {
            chooseCreatingTypeInCreatePopupButton(NEW_MODE)
        }
    }

    static void chooseReportEntity(String entityFullString, String entityName) {
        $j(ReportCreationDialog).with {
            selectEntity(entityFullString)
            checkReportName(getReportBasicName(entityName))
            clickButton(nextBtn)
        }
    }

    static void chooseReportTemplateType(String templateType) {
        $j(ReportCreationDialog).with {
            templateFileTypeField.openOptionsPopup().select(templateType)
        }
    }

    static void chooseFieldsForReport(List<String> stringList) {
        $j(ReportSimpleRegionDialog).with {
            chooseAnyElements(stringList)
            clickButton(ok)
        }
    }

    static void expandTreeNodes(String className) {
        ElementsCollection treeNodes = $$(byClassName(className))
        for (int i = 0, j = 0; i <= treeNodes.size(); i++) {
            treeNodes.get(j).click()
        }
    }

    static void expandReportGroup(String groupName) {
        $(withText(groupName))
                .shouldBe(VISIBLE)
                .click()
    }

    static void removeReport(String reportName) {
        $j(ReportBrowse).with {
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(remove)
        }

        $j(ConfirmationDialog).with {
            clickButton(yes)
        }

        $j(ReportBrowse).with {
            checkRecordIsNotDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }
    }

    static void removeNonSystemGroup(String groupName) {
        $j(ReportGroupBrowse).with {
            selectRowInTableByText(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            clickButton(remove)
        }

        $j(ConfirmationDialog).with {
            clickButton(yes)
        }

        $j(ReportGroupBrowse).with {
            checkRecordIsNotDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
        }
    }

    static void selectReportType(String reportType) {
        $j(ReportCreationDialog).with {
            reportTypeGenerateField.select(reportType)
        }
    }

    static void interruptReportCreating() {
        $j(ReportRegionsDialog).with {
            closeWindow()
        }

        Selenide.sleep(200)

        $j(ConfirmationDialog).with {
            clickButton($j(Button, OPTION_DIALOG_OK_BUTTON_JTEST_ID))
        }
    }

    static void createBasicReportForCompanyEntity(String reportUniqueName) {
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

        $j(ReportEditor).with {
            name.setValue(reportUniqueName)
            clickButton(ok)
        }
    }

    static void chooseParameterForReport() {
        $j(InputParametersDialog).with {
            clickButton(entityPicker)
        }

        $j(CompanyBrowse).with {
            $j(Button, OK_BUTTON_JTEST_ID).shouldBe(DISABLED)
            selectRowInTableByText(COMPANY_TEST_NAME, COMPANY_TABLE_JTEST_ID)
            clickButton($j(Button, OK_BUTTON_JTEST_ID))
        }
    }

    static void runReportAndCloseInputParamsDialog() {
        $j(InputParametersDialog).with {
            clickButton(printReportButton)
            clickButton(cancelButton)
        }
    }

    static void cleanTempFile(String fileNamePath) {
        File file = new File(fileNamePath)
        file.delete()
    }

    static void importReportFile(File reportFile, String reportFileName) {
        $j(ReportBrowse).with {
            clickButton(importBtn)
        }

        $j(ReportImportDialog).with {
            uploadNewDocument(fileUploadField, reportFile)
            checkUploadedFilename(reportFileName)
            clickButton(commitBtn)
            Selenide.sleep(300)
            if (commitBtn.exists()){
                clickButton(commitBtn)
            }
        }
    }
}
