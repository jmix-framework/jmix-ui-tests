package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.WebElement

import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byIndex
import static io.jmix.masquerade.component.DataGrid.SortDirection.ASCENDING
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class DataGridItemClickUiTest extends BaseSamplerUiTest {

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/4
    @Disabled
    @Test
    @DisplayName("Check EnterPress action for DataGrid")
    void checkDataGridEnterPressAction() {
        openSample('datagrid-item-click')
        WebElement row = $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .selectRow(byIndex(0))
        row.sendKeys(ENTER)
        $j(Notification.class)
                .shouldHave(caption('Enter pressed for: Andy Lewis'))
    }

    @Test
    @DisplayName("Check ItemClick action for DataGrid")
    void checkDataGridItemClickAction() {
        openSample('datagrid-item-click')
        WebElement row = $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .selectRow(byIndex(0))
        row.doubleClick()
        $j(Notification.class)
                .shouldHave(caption('Item clicked for: Andy Lewis'))
    }
}
