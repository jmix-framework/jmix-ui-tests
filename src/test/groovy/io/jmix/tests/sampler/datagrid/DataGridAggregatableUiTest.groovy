package io.jmix.tests.sampler.datagrid

import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.DataGrid
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import io.jmix.tests.sampler.test_support.UiSamplesUtils
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selectors.byXpath
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class DataGridAggregatableUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check sum aggregation for dataGrid")
    void checkDataGridSumAggregation() {
        openSample('datagrid-aggregatable')
        def dataGrid = $j(DataGrid.class, 'orderDataGrid')
        double amount = 0
        for (int i = 0; i < 4; i++) {
            def value = dataGrid
                    .getCell(byRowColIndexes(i, 3))
                    .shouldBe(VISIBLE)
                    .getText()
            def newValue = value
                    .replace(" ", "")
                    .replace(",", "")
            amount = amount + (newValue as double)
        }
        def amountValue = UiSamplesUtils.decimalFormat.format(amount)
        getAggregationRowCell(4)
                .shouldHave(text(amountValue))
    }

    @Test
    @DisplayName("Check custom aggregation for dataGrid")
    void checkDataGridCustomAggregation() {
        openSample('datagrid-aggregatable')

        // Sometimes a random click occurs and opens another
        // screen from menu, so move cursor to some safe element
        moveCursorToTab("Description")

        def dataGrid = $j(DataGrid.class, 'orderDataGrid')
                .shouldBe(VISIBLE)
        int amount = 0
        for (int i = 0; i < 4; i++) {
            def value = dataGrid
                    .getCell(byRowColIndexes(i, 2))
                    .shouldBe(VISIBLE)
                    .getText()
            if (value == "Standard") {
                amount++
            }
        }
        getAggregationRowCell(3)
                .shouldHave(text("STANDARD: ${amount}/4"))
    }

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/9
    protected static SelenideElement getAggregationRowCell(int index) {
        return $(byChain(byJTestId('orderDataGrid'),
                byClassName('jmix-aggregation-row'),
                byXpath("(.//th)[${index}]")))
    }
}
