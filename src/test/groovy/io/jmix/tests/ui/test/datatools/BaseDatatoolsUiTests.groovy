package io.jmix.tests.ui.test.datatools

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.user.UserEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.OptionDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*
import static io.jmix.tests.ui.menu.Menus.USER_BROWSE

abstract class BaseDatatoolsUiTests extends BaseUiTest implements ConstantsStrings {

    /**
     * Open Inspector Window
     */
    static void openInspectorWindow(int index) {

        ElementsCollection elementsCollection = $$(byChain(byJTestId('cubaContextMenu'),
                byClassName('c-cm-button')))

        elementsCollection.get(index).click()
    }

    /**
     * Close Inspector Window
     */
    static void closeInspectorWindow() {
        SelenideElement closeBtn = $(byClassName("v-window-closebox"))
        closeBtn.click()
    }

    /**
     * Export entity from Inspector Window
     * @param formatJTestId - j-test-id of button
     * @param tableJTestId - j-test-id of table
     */
    static void exportFromInspectorWindow(String formatJTestId, String tableJTestId) {
        SelenideElement exportBtn = $(byChain(byJTestId(tableJTestId), byClassName("v-popupbutton")))
        exportBtn.click()
        SelenideElement formatBtn = $(byJTestId(formatJTestId))
        formatBtn.click()

    }

    /**
     * Check Notification's appearing and caption
     */
    static void checkNotification(String notificationCaption) {
        $j(Notification).shouldBe(VISIBLE)
                .shouldHave(caption(notificationCaption))
    }

    /**
     * Create new User
     * @param username - user's username
     */
    static void createUser(String username) {
        $j(MainScreen).with {
            sideMenu.openItem(USER_BROWSE)
                    .createUser()
        }

        $j(UserEditor).fillAndSaveUser(username)
    }

    /**
     * Click defined button
     * @param button - defined button
     */
    static void clickButton(Button button) {
        button.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    /**
     * Fill defined field
     * @param field - defined field
     * @param value - defined value
     */
    static void fillTextField(TextField field, String value) {
        field.shouldBe(VISIBLE)
                .shouldBe(EDITABLE)
                .setValue(value)
    }

    /**
     * Remove created instances with Soft Delete trait
     */
    static void wipeOutData(String entityName, String entityFullName, String showMode, String tableJTestId, String name) {
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(entityName, entityFullName)
            selectShowMode(showMode)

            selectRowInTableByText(name, tableJTestId)
            clickButton(wipeOut)
            $j(OptionDialog).confirm()
        }
    }

    /**
     * Remove created instances without Soft Delete trait
     */
    static void cleanData(String entityName, String entityFullName, String showMode, String tableJTestId, String name) {
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(entityName, entityFullName)
            selectShowMode(showMode)

            selectRowInTableByText(name, tableJTestId)
            clickButton(remove)
            $j(ConfirmationDialog).confirm()
        }
    }

    /**
     * Open entity creating screen from Entity inspector browser
     * @param entityname
     * @param entityFullString - full entity name string
     */
    static void openEntityCreatingScreen(String entityName, String entityFullString) {
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(entityName, entityFullString)
            clickButton(create)
        }
    }
}
