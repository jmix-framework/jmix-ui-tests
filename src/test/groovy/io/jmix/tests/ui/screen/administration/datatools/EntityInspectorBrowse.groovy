package io.jmix.tests.ui.screen.administration.datatools

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Table
import io.jmix.tests.ui.screen.administration.datatools.dialogs.OptionDialog
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

class EntityInspectorBrowse extends Composite<EntityInspectorBrowse> {
    @Wire
    ComboBox entitiesLookup

    @Wire
    ComboBox showMode

    @Wire
    Table UserTable_composition

    @Wire
    Button remove

    @Wire
    Button restore

    @Wire
    Button wipeOut



    void findEntityByFilter(String filterValue, String finalValue){
        entitiesLookup.shouldBe(VISIBLE)
                .setFilter(filterValue)
                .getOptionsPopup()
                .select(byText(finalValue))
                .shouldHave(value(finalValue))
    }

    void selectShowMode(String mode){
        showMode.shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(byText(mode))
                .shouldHave(value(mode))
    }

    void selectRowInUserTableByUsername(String username){
        UserTable_composition.shouldBe(VISIBLE)
                .selectRow(byText(username))
                .contextClick()
                .shouldHave(cssClass('v-selected'))
    }

    void clickRemoveButton(){
        remove.shouldBe(VISIBLE)
              .shouldBe(ENABLED)
              .click()
    }

    void clickRestoreButton(){
        restore.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    void clickWipeOutButton(){
        wipeOut.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    void checkRecordIsDisplayed(String username){
        UserTable_composition.shouldBe(VISIBLE)
                .getRow(byText(username))
                .shouldBe(VISIBLE)
    }

    void checkRecordIsNotDisplayed(String username){
        UserTable_composition.shouldBe(VISIBLE)
                .getRow(byText(username))
                .shouldNotBe(VISIBLE)
    }

    void cleanData(String username){
        selectRowInUserTableByUsername(username);
        clickWipeOutButton()
        $j(OptionDialog).confirm()
    }

}
