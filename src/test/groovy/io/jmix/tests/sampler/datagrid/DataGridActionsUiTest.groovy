package io.jmix.tests.sampler.datagrid

import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.WebElement

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$x
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText
import static org.openqa.selenium.Keys.*

@ExtendWith(ChromeExtension)
class DataGridActionsUiTest extends BaseSamplerUiTest {

    public static final String CUSTOMERS_DATA_GRID = 'customersDataGrid'
    public static final String GREETING = 'Greeting'
    public static final String POTTER = 'Potter'
    public static final String NO_SELECTION = 'No selection'
    public static final String CUSTOM_ACTION_NOTIFICATION_MESSAGE = 'Hello, Katherine Potter'

    @Test
    @DisplayName("Check dataGrid custom action on button panel")
    void checkDataGridCustomActionOnButtonPanel() {
        openSample('datagrid-actions')
        def greetingButton = $j(Button.class, 'greeting')
        greetingButton
                .shouldBe(ENABLED)
                .shouldHave(caption(GREETING))
                .click()
        $j(Notification.class)
                .shouldHave(caption(NO_SELECTION))
        def dataGrid = $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .shouldBe(VISIBLE)
        dataGrid
                .selectRow(byText(POTTER))
        greetingButton
                .shouldBe(ENABLED)
                .click()
        $j(Notification.class)
                .shouldHave(caption(CUSTOM_ACTION_NOTIFICATION_MESSAGE))
    }

    @Test
    @DisplayName("Check DataGrid custom action from context menu")
    void checkDataGridCustomActionFromContextMenu() {
        openSample('datagrid-actions')
        def row = selectRow()
        def contextMenu = focusOnContextMenu(row)
        contextMenu
                .findElement(byText(GREETING))
                .click()
        $j(Notification.class)
                .shouldHave(caption(CUSTOM_ACTION_NOTIFICATION_MESSAGE))
    }

    @Test
    @DisplayName("Check DataGrid create action on button panel")
    void checkDataGridCreateActionOnButtonPanel() {
        openSample('datagrid-actions')
        def createButton = $j(Button.class, 'create')
        createButton
                .shouldBe(ENABLED)
                .shouldHave(caption('Create'))
                .click()
        cancelEditor()
    }

    @Test
    @DisplayName("Check DataGrid create action in context menu")
    void checkDataGridCreateActionInContextMenu() {
        openSample('datagrid-actions')

        // Sometimes a random click occurs and opens another
        // screen from menu, so move cursor to some safe element
        moveCursorToTab("Description")

        def row = selectRow()
        def contextMenu = focusOnContextMenu(row)
        contextMenu
                .findElement(byText('Create (Ctrl+\\)'))
                .click()
        cancelEditor()
    }

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/4
    @Disabled
    @Test
    @DisplayName("Check DataGrid create action using shortcut")
    void checkDataGridCreateActionUsingShortcut() {
        openSample('datagrid-actions')
        WebElement row = selectRow()
        def backslash = ALT + NUMPAD9 + NUMPAD2 + ALT
        row.sendKeys(chord(CONTROL, backslash))
        cancelEditor()
    }

    @Test
    @DisplayName("Check DataGrid edit action on button panel")
    void checkDataGridEditActionOnButtonPanel() {
        openSample('datagrid-actions')
        def editButton = $j(Button.class, 'edit')
        editButton
                .shouldNotBe(ENABLED)
                .shouldHave(caption('Edit'))
        selectRow()
        editButton
                .shouldBe(ENABLED)
                .click()
        cancelEditor()
    }

    @Test
    @DisplayName("Check DataGrid edit action in context menu")
    void checkDataGridEditActionInContextMenu() {
        openSample('datagrid-actions')
        def row = selectRow()
        def contextMenu = focusOnContextMenu(row)
        contextMenu
                .findElement(byText('Edit (Enter)'))
                .click()
        cancelEditor()
    }

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/4
    @Disabled
    @Test
    @DisplayName("Check DataGrid edit action using shortcut")
    void checkDataGridEditActionUsingShortcut() {
        openSample('datagrid-actions')
        def row = selectRow()
        row
                .shouldBe(VISIBLE)
                .sendKeys(ENTER)
        cancelEditor()
    }

    @Test
    @DisplayName("Check DataGrid remove action on button panel")
    void checkDataGridRemoveActionOnButtonPanel() {
        openSample('datagrid-actions')
        def removeButton = $j(Button.class, 'remove')
        removeButton
                .shouldNotBe(ENABLED)
                .shouldHave(caption('Remove'))
        selectRow()
        removeButton
                .shouldBe(ENABLED)
                .click()
        cancelRemoving()
    }

    @Test
    @DisplayName("Check DataGrid remove action in context menu")
    void checkDataGridRemoveActionInContextMenu() {
        openSample('datagrid-actions')
        def row = selectRow()
        def contextMenu = focusOnContextMenu(row)
        contextMenu
                .findElement(byText('Remove (Ctrl+Del)'))
                .click()
        cancelRemoving()
    }

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/4
    @Disabled
    @Test
    @DisplayName("Check DataGrid remove action using shortcut")
    void checkDataGridRemoveActionUsingShortcut() {
        openSample('datagrid-actions')
        def row = selectRow()
        row.sendKeys(chord(CONTROL, DELETE))
        cancelRemoving()
    }

    private SelenideElement focusOnContextMenu(def row) {
        row.contextClick()
        $x("//*[contains(@class, 'v-menubar-submenu')]")
    }

    private SelenideElement selectRow() {
        $j(DataGrid.class, CUSTOMERS_DATA_GRID)
                .shouldBe(VISIBLE)
                .selectRow(byText(POTTER))
    }

    private void cancelEditor() {
        $j(Button.class, 'windowClose')
                .shouldBe(ENABLED)
                .click()
    }

    private void cancelRemoving() {
        $('.v-window-modal')
                .shouldBe(VISIBLE)
        $j(Button.class, 'optionDialog_no')
                .shouldBe(ENABLED)
                .click()
    }
}
