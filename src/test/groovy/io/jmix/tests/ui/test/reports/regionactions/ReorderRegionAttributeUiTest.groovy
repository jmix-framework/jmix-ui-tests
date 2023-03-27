package io.jmix.tests.ui.test.reports.regionactions

import com.codeborne.selenide.Selenide
import io.jmix.masquerade.component.PopupButton
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.screen.reports.dialog.ReportRegionsDialog
import io.jmix.tests.ui.screen.reports.dialog.ReportSimpleRegionDialog
import io.jmix.tests.ui.screen.reports.dialog.ReportTabulatedRegionParamsDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,reports'])
class ReorderRegionAttributeUiTest extends BaseReportUiTest {

    @BeforeEach
    void beforeEach() {
        loginAsAdmin()
        maximizeWindowSize()
        $j(MainScreen).openReportsBrowse()
        openReportCreationWizard()
    }

    @Test
    @DisplayName("Reorders attributes in a simple region")
    void reorderAttributesInSimpleRegion() {
        chooseReportEntity(COMPANY_FULL_STRING, COMPANY_ENTITY_NAME)

        def company = "$COMPANY_ENTITY_NAME."
        def companyName = company + COMPANY_TYPE

        def list = [COMPANY_TYPE, COMPANY_EMAIL, COMPANY_GRADE]
        def firstList = getConcatenatedList(list, company)

        def reOrderedList = [COMPANY_EMAIL, COMPANY_TYPE, COMPANY_GRADE]
        def secondList = getConcatenatedList(reOrderedList, company)

        def reOrderStr = getString(reOrderedList)

        $j(ReportSimpleRegionDialog).with {
            chooseAnyElements(list)
            getOrderInGroupBox(firstList)
            selectRowInTableByText(companyName, REPORTS_PROPERTIES_TABLE_JTEST_ID)
            clickButton(downItem)
            getOrderInGroupBox(secondList)
            clickButton(ok)
        }

        $j(ReportRegionsDialog).with {
            checkRecordIsDisplayed(reOrderStr, REGIONS_TABLE_JTEST_ID)
        }
        interruptReportCreating()
    }

    @Test
    @DisplayName("Reorders attributes in a tabulated region")
    void reorderAttributesInTabulatedRegion() {
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

        def atmoGases = "$ATMOSPHERE_ENTITY_NAME.$ATMOSPHERE_GASES."
        def atmoGasesGas = "$atmoGases$GAS_ENTITY_NAME."

        def list1 = [ATMOSPHERIC_GAS_VOLUME, GAS_NAME]

        def firstList = [atmoGases.concat(ATMOSPHERIC_GAS_VOLUME), atmoGasesGas.concat(GAS_NAME)]
        def secondList = [atmoGasesGas.concat(GAS_NAME), atmoGases.concat(ATMOSPHERIC_GAS_VOLUME)]

        $j(ReportSimpleRegionDialog).with {
            expandTreeNodes("collapsed")
            chooseAnyElements(list1)
            getOrderInGroupBox(firstList)
            selectRowInTableByText(atmoGases.concat(ATMOSPHERIC_GAS_VOLUME), REPORTS_PROPERTIES_TABLE_JTEST_ID)
            clickButton(downItem)
            getOrderInGroupBox(secondList)
            clickButton(ok)
        }

        Selenide.sleep(200)
        interruptReportCreating()
    }

}
