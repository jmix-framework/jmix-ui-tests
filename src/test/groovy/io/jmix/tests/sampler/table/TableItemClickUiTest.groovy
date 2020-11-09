package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.Table
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
import static io.jmix.masquerade.component.Table.SortDirection.ASCENDING
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class TableItemClickUiTest extends BaseSamplerUiTest {

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/4
    @Disabled
    @Test
    @DisplayName("Check EnterPress action for table")
    void checkTableEnterPressActionFor() {
        openSample('table-item-click')
        WebElement row = $j(Table.class, 'customerTable')
                .sort('column_name', ASCENDING)
                .selectRow(byIndex(0))
        row.sendKeys(ENTER)
        $j(Notification.class)
                .shouldHave(caption('Enter pressed for: Andy Lewis'))
    }

    @Test
    @DisplayName("Check ItemClick action for table")
    void checkTableItemClickAction() {
        openSample('table-item-click')
        $j(Table.class, 'customerTable')
                .sort('column_name', ASCENDING)
                .selectRow(byIndex(0))
                .doubleClick()
        $j(Notification.class)
                .shouldHave(caption('Item clicked for: Andy Lewis'))
    }
}
