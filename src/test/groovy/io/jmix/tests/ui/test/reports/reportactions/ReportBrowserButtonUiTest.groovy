package io.jmix.tests.ui.test.reports.reportactions

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportExecutionBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
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
class ReportBrowserButtonUiTest extends BaseReportUiTest {

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
    @DisplayName("Exports report")
    void exportReport() {
        expandReportGroup(GROUP_GENERAL_NAME)
        $j(ReportBrowse).with {
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(export)
        }
    }

    @Test
    @DisplayName("Imports and removes imported report")
    void importReport() {
        def reportFileName = getGeneratedString() + REPORT_FILE_NAME
        def reportBasePath = RESOURCES_PATH + REPORT_FILE_NAME
        def reportUniqueFilePath = RESOURCES_PATH + reportFileName
        def reportFile = createNewFile(reportBasePath, reportUniqueFilePath)

        importReportFile(reportFile, reportFileName)

        expandReportGroup(GROUP_GENERAL_NAME)

        $j(ReportBrowse).with {
            checkRecordIsDisplayed(IMPORTED_REPORT_NAME, REPORTS_TABLE_JTEST_ID)
        }

        removeReport(IMPORTED_REPORT_NAME)
        cleanTempFile(reportUniqueFilePath)
    }

    @Test
    @DisplayName("Open report's execution history")
    void executionHistoryReport() {
        expandReportGroup(GROUP_GENERAL_NAME)
        $j(ReportBrowse).with {
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
    }

}
