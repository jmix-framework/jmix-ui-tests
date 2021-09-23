package io.jmix.tests.ui.screen.administration.dynattr

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.OptionDialog

import static com.codeborne.selenide.Condition.text
import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells

class DynamicAttributeBrowse extends Composite<DynamicAttributeBrowse> {
    @Wire
    Button createBtn

    @Wire
    Button editBtn

    @Wire
    Button applyChangesBtn

    @Wire
    Button removeBtn

    @Wire
    Table categoriesTable

    @Wire
    Table attributesTable

    Table getCategoriesTable() {
        return categoriesTable
                .shouldBe(VISIBLE)

    }

    CategoryEditor createCategory() {
        createBtn.shouldBe(VISIBLE)
                .click()
        return new CategoryEditor()
    }

    CategoryEditor editCategory(String name) {
        getCategoriesTable()
                .selectRow(byCells(name))
        editBtn.shouldBe(VISIBLE, ENABLED)
                .click()
        return new CategoryEditor()
    }

    void checkCategory(String name, String isDefault) {
        getCategoriesTable()
                .getRow(byCells(name))
                .shouldBe(VISIBLE)
                .shouldHave(text(isDefault))
    }

    void removeCategory() {
        removeBtn.shouldBe(ENABLED)
                .click()
        $j(ConfirmationDialog)
                .confirmChanges()
    }

    void applyChanges() {
        applyChangesBtn
                .shouldBe(ENABLED)
                .click()
    }

    void checkAttributeTable(String attrName) {
        attributesTable
                .shouldBe(VISIBLE)
                .getRow(byCells(attrName))
                .shouldBe(VISIBLE)
    }
}
