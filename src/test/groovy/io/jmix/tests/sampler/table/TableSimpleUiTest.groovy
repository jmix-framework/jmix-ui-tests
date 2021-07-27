package io.jmix.tests.sampler.table

import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.WebDriverRunner
import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions

import static com.codeborne.selenide.Condition.cssClass
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$x
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*
import static io.jmix.masquerade.component.Table.SortDirection.ASCENDING
import static io.jmix.masquerade.component.Table.SortDirection.DESCENDING

@ExtendWith(ChromeExtension)
class TableSimpleUiTest extends BaseSamplerUiTest {

    public static final String ORDERS_TABLE = 'ordersTable'
    public static final String COLUMN_DATE = 'column_date'
    public static final String FIRST_DATE_VALUE = '26/05/2021'
    public static final String LAST_DATE_VALUE = '30/07/2021'
    public static final String FIRST_ROW_TO_SELECT = 'Katherine Potter'
    public static final String SECOND_ROW_TO_SELECT = 'John Doe'

    @Test
    @DisplayName("Check multiselect options in the table is on")
    void checkTableMultiselectOptionIsOn() {
        openSample('table-simple')
        setMultiselect(true)
        $j(Table.class, ORDERS_TABLE)
                .selectRow(byText(FIRST_ROW_TO_SELECT))
        def secondRow = selectSecondRow()
        secondRow.shouldHave(cssClass('v-selected'))
        $j(Table.class, ORDERS_TABLE)
                .getRow(byText(FIRST_ROW_TO_SELECT))
                .shouldHave(cssClass('v-selected'))
    }

    @Test
    @DisplayName("Check multiselect options in the table is off")
    void checkTableMultiselectOptionIsOff() {
        openSample('table-simple')
        setMultiselect(false)
        $j(Table.class, ORDERS_TABLE)
                .selectRow(byText(FIRST_ROW_TO_SELECT))
        def secondRow = selectSecondRow()

        secondRow.shouldHave(cssClass('v-selected'))
        $j(Table.class, ORDERS_TABLE)
                .getRow(byText(FIRST_ROW_TO_SELECT))
                .shouldNotHave(cssClass('v-selected'))
    }

    @Test
    @DisplayName("Check sortable option is on for table")
    void checkTableSortableOptionIsOn() {
        openSample('table-simple')
        setSortable(true)
        $j(Table.class, ORDERS_TABLE)
                .sort(COLUMN_DATE, DESCENDING)
                .getRow(byRowIndex(0))
                .shouldHave(Condition.text(LAST_DATE_VALUE))

        $j(Table.class, ORDERS_TABLE)
                .sort(COLUMN_DATE, ASCENDING)
                .getRow(byRowIndex(0))
                .shouldHave(Condition.text(FIRST_DATE_VALUE))
    }

    @Test
    @DisplayName("Check sortable option is off for table table")
    void checkTableSortableOptionIsOff() {
        openSample('table-simple')
        setSortable(false)
        $('.v-table-header-cell')
                .shouldNotHave(cssClass('.v-table-header-sortable'))
    }

    @Test
    @DisplayName("Check ColumnControlVisible option is on for table table")
    void checkTableColumnControlVisibleOptionIsOn() {
        openSample('table-simple')
        setColumnControlVisible(true)
        $x("//*[contains(@class, 'v-table-column-selector')]")
                .hover()
                .shouldBe(visible)
                .click()
        $x("//*[contains(@class, 'v-table-column-selector-popup')]")
                .shouldBe(visible)
    }

    @Test
    @DisplayName("Check ColumnControlVisible option is off for table ")
    void checkTableColumnControlVisibleOptionIsOff() {
        openSample('table-simple')
        setColumnControlVisible(false)
        $x("//*[contains(@class, 'v-table-column-selector')]")
                .shouldNotBe(visible)
    }

    @Test
    @DisplayName("Check showSelection option is on for table")
    void checkTableShowSelectionVisibleTableOptionIsOn() {
        openSample('table-simple')
        setShowSelection(true)
        $j(Table.class, ORDERS_TABLE)
                .selectRow(byRowIndex(0))
                .shouldHave(cssClass('v-selected'))
    }

    @Test
    @DisplayName("Check showSelection option is off for table")
    void checkTableShowSelectionVisibleTableOptionIsOff() {
        openSample('table-simple')
        setShowSelection(false)
        $j(Table.class, ORDERS_TABLE)
                .selectRow(byRowIndex(0))
                .shouldNotHave(cssClass('v-selected'))
    }

    private void setSortable(boolean isSortable) {
        $j(CheckBox.class, 'sortable')
                .setChecked(isSortable)
    }

    private void setMultiselect(boolean isMultiselected) {
        $j(CheckBox.class, 'multiselect')
                .setChecked(isMultiselected)
    }

    private void setColumnControlVisible(boolean isAvailable) {
        $j(CheckBox.class, 'columnControlVisible')
                .setChecked(isAvailable)
    }

    private void setShowSelection(boolean isShown) {
        $j(CheckBox.class, 'showSelection')
                .setChecked(isShown)
    }

    private SelenideElement selectSecondRow() {
        SelenideElement secondRow = $j(Table.class, ORDERS_TABLE)
                .getRow(byText(SECOND_ROW_TO_SELECT))
                .shouldBe(VISIBLE)
        Actions actions = new Actions(WebDriverRunner.getWebDriver())
        actions.keyDown(Keys.LEFT_SHIFT)
                .click(secondRow)
                .keyUp(Keys.LEFT_SHIFT)
                .build()
                .perform()
        return secondRow
    }
}
