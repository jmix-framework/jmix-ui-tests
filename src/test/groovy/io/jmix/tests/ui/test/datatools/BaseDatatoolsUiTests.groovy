package io.jmix.tests.ui.test.datatools

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.Notification
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.user.UserEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.OptionDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.*
import static io.jmix.tests.ui.menu.Menus.USER_BROWSE

abstract class BaseDatatoolsUiTests extends BaseUiTest {
    String USER_ENTITY_NAME = "User"
    String USER_FULL_STRING = "User (User)"
    String GAS_ENTITY_NAME = "Gas"
    String GAS_FULL_STRING = "Gas (Gas)"
    String NON_REMOVED_ONLY_MODE = "Show non-removed records only"
    String REMOVED_ONLY_MODE = "Show removed records only"
    String ALL_MODE = "Show all records"
    String ADMIN_USERNAME = "admin"
    String USERNAME1 = "myUser"
    String USERNAME2 = "secondUser"
    String GAS_TABLE_JTEST_ID = "GasTable_composition"
    String USER_TABLE_JTEST_ID = "UserTable_composition"


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
     * Remove created instances with Soft Delete trait
     */
    static void wipeOutData(String entityName, String entityFullName, String showMode, String tableJTestId, String name) {
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(entityName, entityFullName)
            selectShowMode(showMode)

            selectRowInTableByText(name, tableJTestId)
            clickWipeOutButton()
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
            clickRemoveButton()
            $j(ConfirmationDialog).confirm()
        }
    }
}
