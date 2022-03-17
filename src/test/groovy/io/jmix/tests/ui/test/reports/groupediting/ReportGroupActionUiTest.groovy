package io.jmix.tests.ui.test.reports.groupediting

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
class ReportGroupActionUiTest extends BaseReportUiTest {

    public static final String GENERAL_GROUP_NAME = "General"
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
        $j(MainScreen).openReportsGroupBrowse()
    }

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
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)
        def groupCode = getGroupUniqueName(GROUP_TEST_CODE)

        createSimpleSystemGroup(groupName, groupCode)

        $j(ReportGroupBrowse).with {
            checkRecordIsDisplayed(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            selectRowInTableByText(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            clickButton(remove)
        }

        checkNotification(SYSTEM_GROUP_REMOVING_NOTIFICATION)

        makeGroupNonSystem(groupName)
        removeNonSystemGroup(groupName)
    }

    @Test
    @DisplayName("Adds a report to report group")
    void addReportToReportGroup() {
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)

        createSimpleNonSystemGroup(groupName)

        $j(MainScreen).openReportsBrowse()

        $j(ReportBrowse).with {
            expandReportGroup(GROUP_GENERAL_NAME)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            selectValueWithoutFilterInComboBox(group, groupName)
            clickButton(ok)
        }

        $j(ReportBrowse).with {
            expandReportGroup(groupName)
            checkRecordIsDisplayed(reportName, REPORTS_TABLE_JTEST_ID)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            selectValueWithoutFilterInComboBox(group, GENERAL_GROUP_NAME)
            clickButton(ok)
        }

        $j(MainScreen).openReportsGroupBrowse()

        removeNonSystemGroup(groupName)
    }

    @Test
    @DisplayName("Removes report group with reports")
    void removeSimpleReportGroupWithReports() {
        def groupName = getGroupUniqueName(GROUP_TEST_NAME)

        createSimpleNonSystemGroup(groupName)
        $j(MainScreen).openReportsBrowse()

        $j(ReportBrowse).with {
            expandReportGroup(GROUP_GENERAL_NAME)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            selectValueWithoutFilterInComboBox(group, groupName)
            clickButton(ok)
        }

        $j(MainScreen).openReportsGroupBrowse()

        $j(ReportGroupBrowse).with {
            selectRowInTableByText(groupName, REPORTS_GROUPS_TABLE_JTEST_ID)
            clickButton(remove)
        }

        checkNotification(NON_EMPTY_GROUP_REMOVING_NOTIFICATION)

        $j(MainScreen).openReportsBrowse()

        $j(ReportBrowse).with {
            expandReportGroup(groupName)
            selectRowInTableByText(reportName, REPORTS_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(ReportEditor).with {
            selectValueWithoutFilterInComboBox(group, GENERAL_GROUP_NAME)
            clickButton(ok)
        }

        $j(MainScreen).openReportsGroupBrowse()

        removeNonSystemGroup(groupName)
    }
}
