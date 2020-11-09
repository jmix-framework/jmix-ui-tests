package io.jmix.tests.sampler.datagrid

import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.WebDriverRunner
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.DataGrid
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.interactions.Actions

import static com.codeborne.selenide.Condition.cssClass
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*
import static org.openqa.selenium.Keys.LEFT_CONTROL

@ExtendWith(ChromeExtension)
class DataGridSelectionModeUiTest extends BaseSamplerUiTest {

    public static final String SINGLE_MODE = 'SINGLE'
    public static final String MULTI_MODE = 'MULTI'
    public static final String NONE = 'NONE'
    public static final String MULTI_CHECK_MODE = 'MULTI_CHECK'
    public static final String CUSTOMERS_DATA_GRID = 'customersDataGrid'
    public static final String FIRST_ROW_TO_SELECT = 'Potter'
    public static final String SECOND_ROW_TO_SELECT = 'Doe'
    public static final String SELECTED_ROW = 'v-grid-row-selected'

    @Test
    @DisplayName("Check dataGrid single selection mode")
    void checkDataGridSingleSelectionMode() {
        openSample('datagrid-selection-mode')
        setSelectionMode(SINGLE_MODE)
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .selectRow(byText(FIRST_ROW_TO_SELECT))
        SelenideElement secondRow = getSelectedSecondRow()
        secondRow.shouldHave(cssClass(SELECTED_ROW))
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .getRow(byText(FIRST_ROW_TO_SELECT))
                .shouldNotHave(cssClass(SELECTED_ROW))
    }

    @Test
    @DisplayName("Check dataGrid multi selection mode")
    void checkDataGridMultiSelectionMode() {
        openSample('datagrid-selection-mode')
        setSelectionMode(MULTI_MODE)
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .selectRow(byText(FIRST_ROW_TO_SELECT))
        getSelectedSecondRow()
                .shouldHave(cssClass(SELECTED_ROW))
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .getRow(byText(FIRST_ROW_TO_SELECT))
                .shouldHave(cssClass(SELECTED_ROW))
    }

    @Test
    @DisplayName("Check dataGrid multi-check selection mode")
    void checkDataGridMultiCheckSelectionMode() {
        openSample('datagrid-selection-mode')
        setSelectionMode(MULTI_CHECK_MODE)
        //set value of checkbox 'select all' to true
        $(byJTestId('column_0'))
                .click()
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .getRow(byText(FIRST_ROW_TO_SELECT))
                .shouldHave(cssClass(SELECTED_ROW))
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .getRow(byText(SECOND_ROW_TO_SELECT))
                .shouldHave(cssClass(SELECTED_ROW))
    }

    @Test
    @DisplayName("Check dataGrid none selection mode")
    void checkDataGridNoneSelectionMode() {
        openSample('datagrid-selection-mode')
        setSelectionMode(NONE)
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .selectRow(byText(FIRST_ROW_TO_SELECT))
                .shouldNotHave(cssClass(SELECTED_ROW))
    }

    private void setSelectionMode(String selectionMode) {
        $j(ComboBox.class, 'selectionModeField')
                .shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(selectionMode)
    }

    private SelenideElement getSelectedSecondRow() {
        def secondRow = $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .getRow(byText(SECOND_ROW_TO_SELECT))
                .shouldBe(VISIBLE)
        Actions actions = new Actions(WebDriverRunner.getWebDriver())
        actions.keyDown(LEFT_CONTROL)
                .click(secondRow)
                .keyUp(LEFT_CONTROL)
                .build()
                .perform()
        secondRow
    }
}
