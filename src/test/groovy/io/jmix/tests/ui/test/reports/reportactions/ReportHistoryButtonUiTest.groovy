package io.jmix.tests.ui.test.reports.reportactions

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportExecutionBrowse
import io.jmix.tests.ui.screen.reports.dialog.HistoryParametersDialog
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
class ReportHistoryButtonUiTest extends BaseReportUiTest {

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
        expandReportGroup(GROUP_GENERAL_NAME)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(run)
        }

        chooseParameterForReport()
        runReportAndCloseInputParamsDialog()

        $j(ReportBrowse).with {
            clickButton(executionsButton)
        }
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
    @DisplayName("Downloads report's file")
    void downloadReportDocument() {

        $j(ReportExecutionBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_EXECUTIONS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_EXECUTIONS_TABLE_JTEST_ID)
            clickButton(download)
        }
    }

    @Test
    @DisplayName("Downloads report's execution history via Excel file")
    void downloadAllHistoryRecordsExcel() {

        $j(ReportExecutionBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_EXECUTIONS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_EXECUTIONS_TABLE_JTEST_ID)
            clickButton(excel)
        }

        $j(HistoryParametersDialog).with {
            clickButton(all)
        }
    }

    @Test
    @DisplayName("Downloads selected report's execution history via Excel file")
    void downloadSelectedHistoryRecordsExcel() {

        $j(MainScreen).openReportsBrowse()
        expandReportGroup(GROUP_GENERAL_NAME)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(run)
        }

        chooseParameterForReport()
        runReportAndCloseInputParamsDialog()

        $j(ReportBrowse).with {
            clickButton(executionsButton)
        }

        $j(ReportExecutionBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_EXECUTIONS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_EXECUTIONS_TABLE_JTEST_ID)
            clickButton(excel)
        }

        $j(HistoryParametersDialog).with {
            clickButton(selected)
        }
    }
}
