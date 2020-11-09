package io.jmix.tests.sampler.datagrid


import io.jmix.masquerade.component.DataGrid
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.HIDDEN
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class DataGridNoHeaderUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check dataGrid without header")
    void checkDataGridWithoutHeader() {
        openSample('datagrid-no-header')
        $j(DataGrid.class, 'customersDataGrid')
        ['name', 'lastName', 'age', 'active', 'grade']
                .each {
                    $(byChain(byClassName("v-table-header"),
                            byJTestId("column_" + it)))
                            .shouldBe(HIDDEN)
                }
    }
}
