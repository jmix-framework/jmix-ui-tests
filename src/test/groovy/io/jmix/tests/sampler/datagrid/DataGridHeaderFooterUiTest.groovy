package io.jmix.tests.sampler.datagrid

import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.exactText
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId

@ExtendWith(ChromeExtension)
class DataGridHeaderFooterUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check header and footer for DataGrid")
    void checkDataGridHeaderAndFooter() {
        openSample('datagrid-header-footer')
        $(byChain(byJTestId('column_year2014'), byClassName('v-grid-column-header-content')))
                .shouldBe(VISIBLE)
                .shouldHave(exactText("GDP growth"))
        $(byChain(byJTestId('column_country'), byClassName('v-grid-column-footer-content')))
                .shouldBe(VISIBLE)
                .shouldHave(exactText("Average:"))
        $(byChain(byJTestId('column_year2014'), byClassName('v-grid-column-footer-content')))
                .shouldBe(VISIBLE)
                .shouldHave(exactText("4.32%"))
        $(byChain(byJTestId('column_year2015'), byClassName('v-grid-column-footer-content')))
                .shouldBe(VISIBLE)
                .shouldHave(exactText("4.88%"))
    }
}
