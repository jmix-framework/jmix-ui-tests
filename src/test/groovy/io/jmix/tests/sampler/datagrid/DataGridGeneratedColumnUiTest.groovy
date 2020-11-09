package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.DataGrid
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

@ExtendWith(ChromeExtension)
class DataGridGeneratedColumnUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check addGeneratedColumn for DataGrid")
    void checkDataGridGeneratedColumn() {
        openSample('datagrid-generated-column')
        $j(DataGrid.class, 'customersDataGrid')
                .shouldBe(VISIBLE)
                .getCell(byText('Katherine Potter'))
                .shouldBe(VISIBLE)
    }
}
