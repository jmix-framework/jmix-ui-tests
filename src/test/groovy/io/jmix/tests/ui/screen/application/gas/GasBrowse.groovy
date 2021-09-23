package io.jmix.tests.ui.screen.application.gas

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table

import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId

class GasBrowse extends Composite<GasBrowse> {
    @Wire
    Button createBtn

    @Wire
    Table gasesTable

    GasEditor create() {
        createBtn
                .shouldBe(VISIBLE, ENABLED)
                .click()
        return new GasEditor()

    }

    void checkDynamicAttributeVisibility(String attributeName) {
        String columnName = "column_+Gas" + attributeName
        gasesTable
                .shouldBe(VISIBLE)
        $(byChain(byClassName("v-table-header"),
                byJTestId(columnName)))
                .shouldBe(VISIBLE)
    }
}
