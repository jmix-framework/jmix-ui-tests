package io.jmix.tests.ui.test.reports.regionactions

import io.jmix.masquerade.component.PopupButton
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.reports.dialog.ReportRegionsDialog
import io.jmix.tests.ui.screen.reports.dialog.ReportSimpleRegionDialog
import io.jmix.tests.ui.screen.reports.dialog.ReportTabulatedRegionParamsDialog
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
class AddRegionToReportUiTest extends BaseReportUiTest {

    @Test
    @DisplayName("Adds a simple region to report")
    void addSimpleRegionToReport() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportCreationWizard()

        chooseReportEntity(COMPANY_FULL_STRING, COMPANY_ENTITY_NAME)

        def list = [COMPANY_TYPE, COMPANY_EMAIL, COMPANY_GRADE]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            clickButton(addSimpleRegionBtn)
        }

        def list1 = [COMPANY_NAME, COMPANY_REGISTRATION_ID]
        def str1 = getString(list1)
        chooseFieldsForReport(list1)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str1, REGIONS_TABLE_JTEST_ID)
        }
        interruptReportCreating()
    }

    @Test
    @DisplayName("Adds a tabulated region to report")
    void addTabulatedRegionToReport() {
        loginAsAdmin()
        maximizeWindowSize()

        openReportCreationWizard()
        chooseReportEntity(ATMOSPHERE_FULL_STRING, ATMOSPHERE_ENTITY_NAME)
        def list = [ATMOSPHERE_DESCRIPTION, ATMOSPHERE_PRESSURE]
        def str = getString(list)
        chooseFieldsForReport(list)

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(str, REGIONS_TABLE_JTEST_ID)
            $j(PopupButton, ADD_REGION_POPUP_JTEST_ID)
                    .openPopupContent()
                    .select(ADD_REGION_POPUP_NAME)
        }

        $j(ReportTabulatedRegionParamsDialog).with {
            clickTreeNode(ATMOSPHERE_GASES)
            clickButton(ok)
        }

        $j(ReportSimpleRegionDialog).with {
            expandTreeNodes("collapsed")
            def list1 = [ATMOSPHERIC_GAS_VOLUME, GAS_NAME]
            chooseFieldsForReport(list1)
        }

        interruptReportCreating()
    }
}
