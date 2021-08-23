package io.jmix.tests.ui.test.reports.reportactions

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportExecutionBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,reports'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class ReportBrowserButtonUiTest extends BaseReportUiTest {

    @Test
    @DisplayName("Removes report")
    void removeReport() {
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
    @DisplayName("Exports report")
    void exportReport() {
        loginAsAdmin()
        maximizeWindowSize()

        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        createBasicReportForCompanyEntity(reportName)
        $j(ReportBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(export)
        }
        removeReport(reportName)
    }

    @Test
    @DisplayName("Open report's execution history")
    void executionHistoryReport() {
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

        $j(ReportBrowse).with {
            clickButton(executionsButton)
        }

        $j(ReportExecutionBrowse).with {
            checkRecordIsDisplayed(reportName, REPORTS_EXECUTIONS_TABLE_JTEST_ID)
        }

        $j(MainScreen).openReportsBrowse()

        $j(ReportBrowse).with {
            expandReportGroup(REPORT_TABLE_EXPAND_SELECTOR)
        }
        removeReport(reportName)
    }

}
