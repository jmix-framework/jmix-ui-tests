package io.jmix.tests.sampler.table

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class TableNoHeaderUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check table without header")
    void checkTableWithoutHeader() {
        openSample('table-no-header')
        $j(Table.class, 'customerTable')
        ['name', 'lastName', 'age', 'active', 'grade']
                .each {
                    $(byChain(byClassName("v-table-header"),
                            byJTestId("column_" + it)))
                            .shouldBe(Conditions.HIDDEN)
                }
    }
}
