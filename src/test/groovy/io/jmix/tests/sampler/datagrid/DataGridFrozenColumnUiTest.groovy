package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssClass
import static com.codeborne.selenide.Condition.exactText
import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class DataGridFrozenColumnUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check dataGrid frozen columns")
    void checkDataGridFrozenColumns() {
        openSample('datagrid-frozen-columns')
        setFrozenColumnCount('0')
        $j(DataGrid.class, 'customersDataGrid')
                .getHeaderCell("name")
                .shouldHave(exactText("First name"))
                .shouldNotHave(cssClass("frozen"))
        setFrozenColumnCount('2')
        $j(DataGrid.class, 'customersDataGrid')
                .getHeaderCell("name")
                .shouldHave(exactText("First Name"))
                .shouldHave(cssClass("frozen"))
        $j(DataGrid.class, 'customersDataGrid')
                .getHeaderCell("lastName")
                .shouldHave(exactText("Last name"))
                .shouldHave(cssClass("last-frozen"))
    }

    private void setFrozenColumnCount(String columnCount) {
        $j(TextField.class, 'frozenColumnCountField')
                .shouldBe(EDITABLE)
                .setValue(columnCount)
                .delegate
                .sendKeys(ENTER)
    }
}
