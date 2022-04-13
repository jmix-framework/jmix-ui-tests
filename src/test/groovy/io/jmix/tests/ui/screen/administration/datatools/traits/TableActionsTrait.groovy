package io.jmix.tests.ui.screen.administration.datatools.traits

import io.jmix.masquerade.component.Table

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

trait TableActionsTrait {

    static void selectRowInTableByText(String s, String path) {
        $j(Table, path).shouldBe(VISIBLE)
                .selectRow(byText(s))
                .click()
    }

    static void openContextMenuFromSelectedRow(String s, String path) {
        $j(Table, path).shouldBe(VISIBLE)
                .selectRow(byText(s))
                .contextClick()
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