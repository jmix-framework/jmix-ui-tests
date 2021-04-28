package io.jmix.tests.ui.test.datatools

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.Notification
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.dialogs.OptionDialog
import io.jmix.tests.ui.screen.application.user.UserEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.AfterEach

import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byClassName
import static io.jmix.masquerade.Selectors.byJTestId
import static io.jmix.masquerade.Selectors.byText

import static io.jmix.tests.ui.menu.Menus.USER_BROWSE

abstract class BaseDatatoolsUiTests extends BaseUiTest{
    String ENTITY_NAME = "User"
    String USER = "User (User)"
    String NON_REMOVED_ONLY_MODE = "Show non-removed records only"
    String REMOVED_ONLY_MODE = "Show removed records only"
    String ALL_MODE = "Show all records"
    String ADMIN_USERNAME = "admin"
    String USERNAME1 = "myUser"
    String USERNAME2 = "secondUser"

    /**
     * Open Inspector Window
     */
    static void openInspectorWindow(int index){

        ElementsCollection elementsCollection = $$(byChain(byJTestId('cubaContextMenu'),
                byClassName('c-cm-button')))

        elementsCollection.get(index).click()
    }

    /**
     * Close Inspector Window
     */
    static void closeInspectorWindow(){
        SelenideElement closeBtn = $(byClassName("v-window-closebox"))
        closeBtn.click()
    }

    /**
     * Check Notification's appearing and caption
     */
    static void checkNotification(String notificationCaption){
        $j(Notification).shouldBe(VISIBLE)
                .shouldHave(caption(notificationCaption))
    }

    /**
     * Create new User
     * @param username - user's username
     */
    static void createUser(String username){
        $j(MainScreen).with {
            sideMenu.openItem(USER_BROWSE)
                    .createUser()
        }

        $j(UserEditor).fillAndSaveUser(username)
    }

    /**
     * Clean created users if exists
     */
    @AfterEach
    void cleanData(){
        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(ENTITY_NAME, USER)
            selectShowMode(ALL_MODE)
            if (UserTable_composition.getRow(byText(USERNAME1)).is(VISIBLE)){
                selectRowInUserTableByUsername(USERNAME1)
                clickWipeOutButton()
                $j(OptionDialog).confirm()
            }
            if (UserTable_composition.getRow(byText(USERNAME2)).is(VISIBLE)){
                selectRowInUserTableByUsername(USERNAME2)
                clickWipeOutButton()
                $j(OptionDialog).confirm()
            }

        }
    }
}
