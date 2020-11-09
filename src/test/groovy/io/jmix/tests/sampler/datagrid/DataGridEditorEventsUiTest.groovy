package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*
import static io.jmix.masquerade.component.DataGrid.SortDirection.ASCENDING

@ExtendWith(ChromeExtension)
class DataGridEditorEventsUiTest extends BaseSamplerUiTest {

    public static final String NEW_NAME = "NewName"

    @Test
    @DisplayName("Check dataGrid EditorOpenListener")
    void checkDataGridEditorOpenListener() {
        openSample('datagrid-editor-events')
        $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .getCell(byRowColIndexes(0, 0))
                .doubleClick()
        $(byClassName('v-grid-editor-save'))
                .shouldBe(VISIBLE)
        $(byClassName('v-grid-editor-cancel'))
                .shouldBe(VISIBLE)
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("Editor will open"))
    }

    @Test
    @DisplayName("Check dataGrid EditorCloseListener")
    void checkDataGridEditorCloseListener() {
        openSample('datagrid-editor-events')
        $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .getCell(byRowColIndexes(0, 0))
                .doubleClick()
        $(byClassName('v-grid-editor-save'))
                .shouldBe(VISIBLE)
        $(byClassName('v-grid-editor-cancel'))
                .shouldBe(VISIBLE)
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("Editor will open"))
                .clickToClose()
        $j(TextField.class, 'name')
                .shouldBe(EDITABLE)
                .setValue(NEW_NAME)
        $(byClassName('v-grid-editor-cancel'))
                .shouldBe(VISIBLE)
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("Editor closed"))
                .clickToClose()
        $j(DataGrid.class, 'customersDataGrid')
                .getRow(byCells("Andy", "Lewis", "35"))
                .shouldBe(VISIBLE)
    }

    @Test
    @DisplayName("Check dataGrid EditorPostCommitListener")
    void checkDataGridEditorPostCommitListener() {
        openSample('datagrid-editor-events')
        $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .getCell(byRowColIndexes(0, 0))
                .doubleClick()
        $(byClassName('v-grid-editor-save'))
                .shouldBe(VISIBLE)
        $(byClassName('v-grid-editor-cancel'))
                .shouldBe(VISIBLE)
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("Editor will open"))
                .clickToClose()
        $j(TextField.class, 'name')
                .shouldBe(EDITABLE)
                .setValue(NEW_NAME)
        $(byClassName('v-grid-editor-save'))
                .shouldBe(VISIBLE)
                .click()
        $('.v-Notification-tray')
                .shouldBe(VISIBLE)
                .shouldHave(textCaseSensitive("Pre Commit"))
                .click()
        $('.v-Notification-humanized')
                .shouldBe(VISIBLE)
                .shouldHave(textCaseSensitive("Post Commit"))
                .click()
        $j(DataGrid.class, 'customersDataGrid')
                .getRow(byCells(NEW_NAME, "Lewis", "35"))
                .shouldBe(VISIBLE)
    }
}
