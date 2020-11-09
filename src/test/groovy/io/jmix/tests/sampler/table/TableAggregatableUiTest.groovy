package io.jmix.tests.sampler.table

import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.text.NumberFormat

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selectors.byXpath
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class TableAggregatableUiTest extends BaseSamplerUiTest {
    @Test
    @DisplayName("Check aggregation with type = 'sum' for table")
    void checkTableAggregationSum() {
        openSample('table-aggregatable')
        def table = $j(Table.class, 'orderTable')
        int amount = 0
        for (int i = 0; i < 4; i++) {
            def value = table
                    .getCell(byRowColIndexes(i, 3))
                    .shouldBe(VISIBLE)
                    .getText()
            def newValue = value.replace(" ", "")
                    .replace(",", "")
            amount = amount + (newValue as int)
        }
        def amountValue = NumberFormat.getInstance().format(amount)
        getAggregationRowCell(4)
                .shouldHave(text(amountValue))
    }

    @Test
    @DisplayName("Check custom aggregation for table")
    void checkTableCustomAggregation() {
        openSample('table-aggregatable')
        def table = $j(Table.class, 'orderTable')
        int amount = 0
        for (int i = 0; i < 4; i++) {
            def value = table.getCell(byRowColIndexes(i, 2))
                    .shouldBe(VISIBLE)
                    .getText()
            if (value == "Standard") {
                amount++
            }
        }
        getAggregationRowCell(3)
                .shouldHave(text('STANDARD: ' + amount + '/4'))
    }

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/9
    protected static SelenideElement getAggregationRowCell(int index) {
        return $(byChain(byJTestId('orderTable'),
                byClassName('v-table-arow'),
                byXpath("(.//td)[" + index + "]")))
    }
}
