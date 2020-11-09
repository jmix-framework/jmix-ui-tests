package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.Label
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.valueContains
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

@ExtendWith(ChromeExtension)
class DataGridDetailsGeneratorUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check dataGrid DetailsGenerator")
    void checkDataGridDetailsGenerator() {
        openSample('datagrid-details-generator')
        $j(DataGrid.class, 'ordersDataGrid')
                .shouldBe(VISIBLE)
                .getCell(byText("24,990"))
                .doubleClick()
        $j(Label.class, 'contentLabel')
                .shouldBe(VISIBLE)
                .shouldHave(valueContains("24990"))
        $j(Button.class, 'closeAction')
                .shouldBe(VISIBLE)
                .click()
        $j(Label.class, 'contentLabel')
                .shouldNotBe(VISIBLE)
        $j(Button.class, 'closeAction')
                .shouldNotBe(VISIBLE)
    }
}
