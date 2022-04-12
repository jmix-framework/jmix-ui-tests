package io.jmix.tests.ui.test.reports.reportactions

import com.codeborne.selenide.Selenide
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
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RunReportUiTest extends BaseReportUiTest {

    public String reportName = ""

    @BeforeAll
    void beforeAll() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).openReportsBrowse()

        reportName = getReportUniqueName(COMPANY_ENTITY_NAME)
        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
        }

        $j(MainScreen).logout()
    }

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).openReportsBrowse()
    }

    @AfterAll
    void afterAll() {
        loginAsAdmin()
        maximizeWindowSize()

        $j(MainScreen).openReportsBrowse()
        expandReportGroup(GROUP_GENERAL_NAME)

        removeReport(reportName)
        $j(MainScreen).logout()
    }


    @Test
    @DisplayName("Runs existing report from report browser")
    void runExistingReport() {
        expandReportGroup(GROUP_GENERAL_NAME)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(run)
        }
        chooseParameterForReport()

        runReportAndCloseInputParamsDialog()
    }

    @Test
    @DisplayName("Runs report from entity browser")
    void runReportFromEntityBrowser() {
        expandReportGroup(GROUP_GENERAL_NAME)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            openTab(SECURITY_TAB_JTEST_ID)
            selectValueWithoutFilterInComboBox($j(ComboBox, COMBOBOX_SCREENS_JTEST_ID), REPORT_SCREEN_NAME)
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
    }

    @Test
    @DisplayName("Runs report from Show tables screen")
    void runReportFromShowTablesScreen() {
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

        $j(SaveReportDialog).save()

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
            selectValueWithoutFilterInComboBox(reports, reportName)
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
        expandReportGroup(GROUP_GENERAL_NAME)
        removeReport(reportName)
    }

    @Test
    @DisplayName("Runs report from report editor")
    void runReportFromEditor() {
        expandReportGroup(GROUP_GENERAL_NAME)

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
            Selenide.refresh()
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Runs report from JPQL query dialog window")
    void runReportFromJPQLQueryEditor() {
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
        $j(MainScreen).openReportsRunScreen()

        $j(ReportRunScreen).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(run)
        }
        chooseParameterForReport()

        runReportAndCloseInputParamsDialog()
    }
}
