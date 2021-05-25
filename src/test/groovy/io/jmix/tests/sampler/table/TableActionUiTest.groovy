package io.jmix.tests.sampler.table

import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.Table
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
class TableActionUiTest extends BaseSamplerUiTest {
    @Test
    @DisplayName("Check table custom action on button panel")
    void checkTableCustomActionOnButtonPanel() {
        openSample('table-action')

        def greetingButton = $j(Button.class, 'greeting')
        greetingButton.shouldBe(ENABLED)
                .shouldHave(caption('Greeting'))
                .click()
        $j(Notification.class)
                .shouldHave(caption('No selection'))

        def table = $j(Table.class, 'customerTable')
                .shouldBe(VISIBLE)
        table.selectRow(byText('Potter'))
        greetingButton.shouldBe(ENABLED).click()
        $j(Notification.class)
                .shouldHave(caption('Hello, Katherine Potter'))
    }

    @Test
    @DisplayName("Check table custom action from context menu")
    void checkTableCustomActionFromContextMenu() {
        openSample('table-action')
        def row = selectRow()
        def contextMenu = focusOnContextMenu(row)
        contextMenu.findElement(byText("Greeting"))
                .click()
        $j(Notification.class)
                .shouldHave(caption('Hello, Katherine Potter'))
    }

    @Test
    @DisplayName("Check table create action on button panel")
    void checkTableCreateActionOnButtonPanel() {
        openSample('table-action')
        def createButton = $j(Button.class, 'create')
        createButton.shouldBe(ENABLED)
                .shouldHave(caption('Create'))
                .click()
        cancelEditor()
    }

    @Test
    @DisplayName("Check table create action in context menu")
    void checkTableCreateActionInContextMenu() {
        openSample('table-action')
        def row = selectRow()
        def contextMenu = focusOnContextMenu(row)
        contextMenu.findElement(byText('Create (Ctrl+\\)'))
                .click()
        cancelEditor()
    }

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/4
    @Disabled
    @Test
    @DisplayName("Check table create action using shortcut")
    void checkTableCreateActionUsingShortcut() {
        openSample('table-action')
        WebElement row = selectRow()
        def backslash = ALT + NUMPAD9 + NUMPAD2 + ALT
        row.sendKeys(chord(CONTROL, backslash))
        cancelEditor()
    }

    @Test
    @DisplayName("Check table edit action on button panel")
    void checkTableEditActionOnButtonPanel() {
        openSample('table-action')
        def editButton = $j(Button.class, 'edit')
        editButton.shouldNotBe(ENABLED)
                .shouldHave(caption('Edit'))
        selectRow()
        editButton.shouldBe(ENABLED)
                .click()
        cancelEditor()
    }

    @Test
    @DisplayName("Check table edit action in context menu")
    void checkTableEditActionInContextMenu() {
        openSample('table-action')
        def row = selectRow()
        def contextMenu = focusOnContextMenu(row)
        contextMenu.findElement(byText('Edit (Enter)'))
                .click()
        cancelEditor()
    }

    //TODO https://github.com/Haulmont/jmix-masquerade/issues/4
    @Disabled
    @Test
    @DisplayName("Check table edit action using shortcut")
    void checkTableEditActionUsingShortcut() {
        openSample('table-action')
        def row = selectRow()
        row.shouldBe(VISIBLE).sendKeys(ENTER)
        cancelEditor()
    }

    @Test
    @DisplayName("Check table remove action on button panel")
    void checkTableRemoveActionOnButtonPanel() {
        openSample('table-action')
        def removeButton = $j(Button.class, 'remove')
        removeButton
                .shouldNotBe(ENABLED)
                .shouldHave(caption('Remove'))
        selectRow()
        removeButton.shouldBe(ENABLED)
                .click()
        cancelRemoving()
    }

    @Test
    @DisplayName("Check table remove action in context menu")
    void checkTableRemoveActionInContextMenu() {
        openSample('table-action')
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
    @DisplayName("Check table remove action using shortcut")
    void checkTableRemoveActionUsingShortcut() {
        openSample('table-action')
        def row = selectRow()
        row.sendKeys(chord(CONTROL, DELETE))
        cancelRemoving()
    }

    private void cancelRemoving() {
        $('.v-window-modal').shouldBe(VISIBLE)
        $j(Button.class, 'optionDialog_no')
                .shouldBe(ENABLED)
                .click()
    }

    private SelenideElement focusOnContextMenu(def row) {
        row.contextClick()
        $x("//*[contains(@class, 'jmix-context-menu')]//*[contains(@class, 'v-verticallayout-jmix-cm-container')]")
    }

    private SelenideElement selectRow() {
        $j(Table.class, 'customerTable')
                .shouldBe(VISIBLE)
                .selectRow(byText('Potter'))
    }

    private void cancelEditor() {
        $j(Button.class, 'windowClose')
                .shouldBe(ENABLED)
                .click()
    }
}
