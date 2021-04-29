package io.jmix.tests.ui.screen.administration.datatools

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Table

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

class EntityInspectorBrowse extends Composite<EntityInspectorBrowse> {
    @Wire
    ComboBox entitiesLookup

    @Wire
    ComboBox showMode

    @Wire
    Button create

    @Wire
    Button edit

    @Wire
    Button remove

    @Wire
    Button restore

    @Wire
    Button wipeOut

    void findEntityByFilter(String filterValue, String finalValue) {
        entitiesLookup.shouldBe(VISIBLE)
                .setFilter(filterValue)
                .getOptionsPopup()
                .select(byText(finalValue))
                .shouldHave(value(finalValue))
    }

    void selectShowMode(String mode) {
        showMode.shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(byText(mode))
                .shouldHave(value(mode))
    }

    static void selectRowInTableByText(String s, String path) {
        $j(Table, path).shouldBe(VISIBLE)
                .selectRow(byText(s))
                .contextClick()
                .shouldHave(cssClass('v-selected'))
    }

    void clickRemoveButton() {
        remove.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    void clickRestoreButton() {
        restore.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    void clickWipeOutButton() {
        wipeOut.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    void clickCreateButton() {
        create.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    void clickEditButton() {
        edit.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }


    static void checkRecordIsDisplayed(String s, String path) {
        $j(Table, path).shouldBe(VISIBLE)
                .getRow(byText(s))
                .shouldBe(VISIBLE)
    }

    static void checkRecordIsNotDisplayed(String s, String path) {
        $j(Table, path).shouldBe(VISIBLE)
                .getRow(byText(s))
                .shouldNotBe(VISIBLE)
    }


}
