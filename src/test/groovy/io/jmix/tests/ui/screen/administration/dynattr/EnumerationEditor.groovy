package io.jmix.tests.ui.screen.administration.dynattr


import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.TextField

import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells

class EnumerationEditor extends Composite<EnumerationEditor> {
    @Wire(path = 'localizedEnumValuesDataGrid')
    DataGrid dataGrid

    @Wire(path = 'addBtn')
    Button add

    @Wire(path = 'commitBtn')
    Button ok

    @Wire
    TextField valueField

    @Wire
    DataGrid localizedValuesDataGrid

    void addEnumValue(String value) {
        valueField
                .shouldBe(VISIBLE)
                .setValue(value)
        add.shouldBe(VISIBLE)
                .click()
        dataGrid
                .shouldBe(VISIBLE)
                .getRow(byCells(value))
                .shouldBe(VISIBLE)
    }

    void saveChanges() {
        ok.shouldBe(VISIBLE)
                .click()
    }

    void setLocalizedValue(String locale, String nameValue) {
        localizedValuesDataGrid
                .shouldBe(VISIBLE)
                .selectRow(byCells(locale))
                .doubleClick()
        $j('name')
                .shouldBe(VISIBLE)
                .setValue(nameValue)
        $(byClassName('v-grid-editor-save'))
                .click()
    }
}
