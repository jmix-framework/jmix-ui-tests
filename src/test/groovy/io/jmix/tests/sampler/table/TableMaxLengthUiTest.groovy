package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.value
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells

@ExtendWith(ChromeExtension)
class TableMaxLengthUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check max length text")
    void checkMaxLengthText() {
        openSample('table-max-length')
        $j(Table.class, 'orderTable')
                .getRow(byCells('Of...'))
                .shouldBe(VISIBLE)
                .findElement(byClassName('jmix-table-clickable-cell'))
                .click()
        $('.v-textarea-readonly')
                .shouldBe(VISIBLE)
                .shouldHave(value("Office equipment"))
    }
}
