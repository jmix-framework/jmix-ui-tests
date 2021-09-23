package io.jmix.tests.ui.screen.application.discounts

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId

class DiscountBrowser extends Composite<DiscountBrowser> {

    @Wire
    Table discountsesTable

    @Wire
    Button createBtn

    void checkDynamicAttributeVisibility(String attributeName) {
        String columnName = "column_+DiscountsLocaleLocalization_En"
        discountsesTable
                .shouldBe(VISIBLE)
        $(byChain(byClassName("v-table-header"),
                byJTestId(columnName)))
                .shouldBe(VISIBLE)
                .shouldHave(textCaseSensitive(attributeName))
    }

    void create() {
        createBtn
                .shouldBe(VISIBLE)
                .click()
    }
}
