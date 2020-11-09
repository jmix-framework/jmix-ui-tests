package io.jmix.tests.sampler.datagrid

import com.codeborne.selenide.Condition
import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.*
import static io.jmix.masquerade.component.DataGrid.SortDirection.DESCENDING

@ExtendWith(ChromeExtension)
class DataGridRendererUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check ImageRenderer for dataGrid")
    void checkDataGridImageRenderer() {
        openSample('datagrid-renderer')
        $j(DataGrid.class, 'dataGrid')
                .shouldBe(VISIBLE)
                .getCell(byRowColIndexes(0, 0))
                .$(byClassName("gwt-Image"))
                .shouldBe(VISIBLE)
    }

    @Test
    @DisplayName("Check ClickableTextRenderer for dataGrid")
    void checkDataGridClickableTextRenderer() {
        openSample('datagrid-renderer')
        def countryClickable = $j(DataGrid.class, 'dataGrid')
                .shouldBe(VISIBLE)
                .getCell(byRowColIndexes(0, 1))
                .$(byClassName("v-link"))
        String countryValue = countryClickable.getText()
        countryClickable.click()
        $j(Notification.class)
                .shouldHave(caption(countryValue))
    }

    @Test
    @DisplayName("Check NumberRenderer percentFormat for dataGrid")
    void checkDataGridNumberRenderer() {
        openSample('datagrid-renderer')
        $j(DataGrid.class, 'dataGrid')
                .sort("column_year2015", DESCENDING)
                .getCell(byRowColIndexes(0, 3))
                .shouldHave(Condition.text('10.1%'))
    }
}
