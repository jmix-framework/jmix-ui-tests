package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.DataGrid
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssClass
import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$x
import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*
import static io.jmix.masquerade.component.DataGrid.SortDirection.ASCENDING
import static io.jmix.masquerade.component.DataGrid.SortDirection.DESCENDING

@ExtendWith(ChromeExtension)
class DataGridBasicSettingsUiTest extends BaseSamplerUiTest {

    public static final String COLUMN_DATE = "column_date"
    public static final String FIRST_DATE_VALUE = '01/07/2020'
    public static final String LAST_DATE_VALUE = '30/07/2020'
    public static final String DATA_GRID_TABLE = 'ordersDataGrid'

    @Test
    @DisplayName("Checks sortable option is on for dataGrid")
    void checkDataGridSortableOptionIsOn() {
        openSample('datagrid-basic-settings')
        setSortable(true)
        $j(DataGrid.class, DATA_GRID_TABLE)
                .shouldBe(VISIBLE)
                .sort(COLUMN_DATE, ASCENDING)
                .getRow(byRowIndex(0))
                .shouldHave(text(FIRST_DATE_VALUE))
        $j(DataGrid.class, DATA_GRID_TABLE)
                .shouldBe(VISIBLE)
                .sort(COLUMN_DATE, DESCENDING)
                .getRow(byRowIndex(0))
                .shouldHave(text(LAST_DATE_VALUE))
    }

    @Test
    @DisplayName("Checks sortable option is off for dataGrid")
    void checkDataGridSortableOptionIsOff() {
        openSample('datagrid-basic-settings')
        setSortable(false)
        $(byJTestId(COLUMN_DATE))
                .shouldNotHave(cssClass('sortable'))
    }

    @Test
    @DisplayName("Checks columnsCollapsingAllowed option is on for dataGrid")
    void checkDataGridColumnsCollapsingAllowedOptionIsOn() {
        openSample('datagrid-basic-settings')
        setColumnControlVisible(true)
        $x("//*[contains(@class, 'v-grid-sidebar-button')]")
                .hover()
                .shouldBe(VISIBLE)
                .click()
        $x("//*[contains(@class, 'v-grid-sidebar-popup')]")
                .shouldBe(VISIBLE)
    }

    @Test
    @DisplayName("Checks columnsCollapsingAllowed option is off for ащк dataGrid")
    void checkDataGridColumnsCollapsingAllowedOptionIsOff() {
        openSample('datagrid-basic-settings')
        setColumnControlVisible(false)
        $x("//*[contains(@class, 'v-grid-sidebar')]")
                .shouldNotBe(VISIBLE)
    }

    private CheckBox setSortable(boolean sortable) {
        $j(CheckBox.class, 'sortable')
                .shouldBe(EDITABLE)
                .setChecked(sortable)
    }

    private void setColumnControlVisible(boolean visible) {
        $j(CheckBox.class, 'columnsCollapsingAllowed')
                .shouldBe(EDITABLE)
                .setChecked(visible)
    }
}
