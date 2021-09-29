package io.jmix.tests.ui.test.reports.groupediting

import io.jmix.masquerade.component.Notification
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportGroupBrowse
import io.jmix.tests.ui.screen.reports.editor.ReportGroupEditor
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
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
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,reports'])
@ContextConfiguration(initializers = TestContextInitializer)
class ReportGroupActionUiTest extends BaseReportUiTest {

    static void createSimpleNonSystemGroup(String groupName) {
        $j(ReportGroupBrowse).with {
            clickButton(create)
        }

        $j(ReportGroupEditor).with {
            setGroupTitle(groupName)
            clickButton(ok)
        }
    }

    static void createSimpleSystemGroup(String groupName, String groupCode) {
        $j(ReportGroupBrowse).with {
            clickButton(create)
        }

        $j(ReportGroupEditor).with {
            setGroupTitle(groupName)
            setSystemCode(groupCode)
            clickButton(ok)
        }
    }

    static void makeGroupNonSystem(String groupName) {
        $j(ReportGroupBrowse).with {
            selectRowInTableByText(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportGroupEditor).with {
            setSystemCode(EMPTY_STRING)
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Creates a non-system report group")
    void createNonSystemReportGroup() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)
        createSimpleNonSystemGroup(groupName)

        $j(ReportGroupBrowse).with {
            checkRecordIsDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
        }

        removeNonSystemGroup(groupName)
    }


    @Test
    @DisplayName("Creates a system report group")
    void createSystemReportGroup() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }

        def groupName = getGroupUniqueName(GROUP_TEST_NAME)
        def groupCode = getGroupUniqueName(GROUP_TEST_CODE)

        createSimpleSystemGroup(groupName, groupCode)

        $j(ReportGroupBrowse).with {
            checkRecordIsDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
        }

        makeGroupNonSystem(groupName)

        removeNonSystemGroup(groupName)
    }

    @Test
    @DisplayName("Edits a report group")
    void editReportGroup() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)

        createSimpleNonSystemGroup(groupName)

        $j(ReportGroupBrowse).with {
            checkRecordIsDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            selectRowInTableByText(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        def groupName1 = getGroupUniqueName(GROUP_TEST_NAME)
        $j(ReportGroupEditor).with {
            setGroupTitle(groupName1)
            clickButton(ok)
        }

        $j(ReportGroupBrowse).with {
            checkRecordIsDisplayed(groupName1, REPORTS_GROUPS_TABLE_JTEST_ID)
            checkRecordIsNotDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
        }

        removeNonSystemGroup(groupName1)
    }

    @Test
    @DisplayName("Removes a non-system report group without reports")
    void removeNonSystemReportGroup() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)

        createSimpleNonSystemGroup(groupName)

        $j(ReportGroupBrowse).with {
            checkRecordIsDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
        }

        removeNonSystemGroup(groupName)
    }

    @Test
    @DisplayName("Removes a system report group")
    void removeSystemReportGroup() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)
        def groupCode = getGroupUniqueName(GROUP_TEST_CODE)

        createSimpleSystemGroup(groupName, groupCode)

        $j(ReportGroupBrowse).with {
            checkRecordIsDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            selectRowInTableByText(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            clickButton(remove)
        }

        checkNotification(SYSTEM_GROUP_REMOVING_NOTIFICATION)
        $j(Notification).clickToClose()

        makeGroupNonSystem(groupName)

        removeNonSystemGroup(groupName)
    }

    @Test
    @DisplayName("Adds a report to report group")
    void addReportToReportGroup() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)

        createSimpleNonSystemGroup(groupName)
        $j(MainScreen).with {
            openReportsBrowse()
        }
        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            group.openOptionsPopup().select(groupName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            expandReportGroup(REPORT_TABLE_EXPAND_SELECTOR)
        }

        removeReport(reportName)
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        removeNonSystemGroup(groupName)
    }

    @Test
    @DisplayName("Removes report group with reports")
    void removeSimpleReportGroupWithReports() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)

        createSimpleNonSystemGroup(groupName)
        $j(MainScreen).with {
            openReportsBrowse()
        }
        def reportName = getReportUniqueName(COMPANY_ENTITY_NAME)

        createBasicReportForCompanyEntity(reportName)

        $j(ReportBrowse).with {
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            group.openOptionsPopup().select(groupName)
            clickButton(ok)
        }

        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        $j(ReportGroupBrowse).with {
            selectRowInTableByText(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            clickButton(remove)
        }

        checkNotification(NON_EMPTY_GROUP_REMOVING_NOTIFICATION)
        $j(Notification).clickToClose()

        $j(MainScreen).with {
            openReportsBrowse()
        }

        $j(ReportBrowse).with {
            expandReportGroup(REPORT_TABLE_EXPAND_SELECTOR)
        }
        removeReport(reportName)

        $j(MainScreen).with {
            openReportsGroupBrowse()
        }
        removeNonSystemGroup(groupName)
    }

}
