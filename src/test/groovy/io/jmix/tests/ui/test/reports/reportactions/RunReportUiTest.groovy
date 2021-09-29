package io.jmix.tests.ui.test.reports.reportactions

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Table
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.application.company.CompanyBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.dialog.JPQLQueryEditorDialog
import io.jmix.tests.ui.screen.reports.dialog.ReportRegionsDialog
import io.jmix.tests.ui.screen.reports.dialog.SaveReportDialog
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.screen.reports.screen.ReportRunScreen
import io.jmix.tests.ui.screen.reports.screen.ShowReportTableScreen
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.DISABLED
import static io.jmix.masquerade.Conditions.VISIBLE
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
class RunReportUiTest extends BaseReportUiTest {

    @Test
    @DisplayName("Runs existing report from report browser")
    void runExistingReport() {
        loginAsAdmin()
        maximizeWindowSize()
        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(run)
        }
        chooseParameterForReport()

        runReportAndCloseInputParamsDialog()

        removeReport(reportName)
    }

    @Test
    @DisplayName("Runs report from entity browser")
    void runReportFromEntityBrowser() {
        loginAsAdmin()
        maximizeWindowSize()

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)
        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            openTab(SECURITY_TAB_JTEST_ID)
            $j(ComboBox, COMBOBOX_SCREENS_JTEST_ID).openOptionsPopup()
                    .select(REPORT_SCREEN_NAME)
            clickButton($j(Button, SCREEN_ADD_BUTTON_JTEST_ID))

            checkRecordIsDisplayed(REPORT_SCREEN_NAME, REPORTS_SCREENS_JTEST_ID)
            clickButton(ok)
        }

        $j(MainScreen).openCompanyBrowse()

        $j(CompanyBrowse).with {
            checkRecordIsDisplayed(COMPANY_TEST_NAME, COMPANY_TABLE_JTEST_ID)
            selectRowInTableByText(COMPANY_TEST_NAME, COMPANY_TABLE_JTEST_ID)
            clickButton(printBtn)
        }
        $j(MainScreen).openReportsBrowse()
        expandReportGroup(REPORT_TABLE_EXPAND_SELECTOR)
        removeReport(reportName)
    }

    @Test
    @DisplayName("Runs report from Show tables screen")
    void runReportFromShowTablesScreen() {
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
        $j(MainScreen).openReportsShowTablesScreen()

        $j(ShowReportTableScreen).with {
            reports.openOptionsPopup().select(reportName)
            clickButton($j(Button, "tag_lookup"))
        }

        $j(CompanyBrowse).with {
            $j(Button, OK_BUTTON_JTEST_ID).shouldBe(DISABLED)
            selectRowInTableByText(COMPANY_TEST_NAME, COMPANY_TABLE_JTEST_ID)
            clickButton($j(Button, OK_BUTTON_JTEST_ID))
        }

        $j(ShowReportTableScreen).with {
            clickButton($j(Button, "printReportBtn"))
            $j(Table, COMPANY_SHOW_TABLES_JTEST_ID).shouldBe(VISIBLE)
            checkRecordIsDisplayed(COMPANY_TEST_EMAIL, COMPANY_SHOW_TABLES_JTEST_ID)
            clickButton($j(Button, "excelExport"))
        }

        $j(MainScreen).openReportsBrowse()
        expandReportGroup(REPORT_TABLE_EXPAND_SELECTOR)
        removeReport(reportName)
    }

    @Test
    @DisplayName("Runs report from report editor")
    void runReportFromEditor() {
        loginAsAdmin()
        maximizeWindowSize()
        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            clickButton(run)
        }

        chooseParameterForReport()
        runReportAndCloseInputParamsDialog()

        $j(ReportEditor).with {
            clickButton(ok)
        }

        removeReport(reportName)
    }

    @Test
    @DisplayName("Runs report from JPQL query dialog window")
    void runReportFromJPQLQueryEditor() {
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
            clickButton(runBtn)
        }

        interruptReportCreating()
    }

    @Test
    @DisplayName("Runs report from second step (region editing dialog)")
    void runReportFromSecondStep() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportCreationWizard()

        chooseReportEntity(COMPANY_FULL_STRING, COMPANY_ENTITY_NAME)

        def list = [COMPANY_TYPE, COMPANY_EMAIL, COMPANY_GRADE]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(runBtn)
        }

        chooseParameterForReport()
        runReportAndCloseInputParamsDialog()

        interruptReportCreating()
    }

    @Test
    @DisplayName("Runs report from report run screen")
    void runReportFromReportRunScreen() {
        loginAsAdmin()
        maximizeWindowSize()

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)
        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
        }

        $j(MainScreen).with {
            openReportsRunScreen()
        }

        $j(ReportRunScreen).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(run)
        }
        chooseParameterForReport()

        runReportAndCloseInputParamsDialog()
        $j(MainScreen).with {
            openReportsBrowse()
        }
        $j(ReportBrowse).with {
            expandReportGroup(REPORT_TABLE_EXPAND_SELECTOR)
        }

        removeReport(reportName)
    }
}
