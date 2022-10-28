package io.jmix.tests.ui.screen.administration.dynattr

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.*

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*
import static org.openqa.selenium.Keys.ENTER

class CategoryEditor extends Composite<CategoryEditor> {

    @Wire(path = 'nameField')
    TextField nameField

    @Wire(path = 'entityTypeField')
    ComboBox entityType

    @Wire(path = 'isDefaultField')
    CheckBox isDefault

    @Wire(path = 'windowCommitAndClose')
    Button ok

    @Wire
    Button createBtn

    @Wire
    Button editBtn

    @Wire
    Button removeBtn

    @Wire
    Table categoryAttrsTable

    @Wire
    TextField localizedNameField

    @Wire(path = 'localizedValuesDataGrid')
    DataGrid tableLocalization

    void saveCategory() {
        ok.shouldBe(VISIBLE)
                .click()
    }

    void setDefault(boolean isTrue) {
        isDefault.shouldBe(EDITABLE)
                .setChecked(isTrue)
                .setChecked(isTrue)
    }


    void setEntityType(String type) {
        entityType.shouldBe(VISIBLE)
                .setFilter(type)
                .delegate
                .$(byClassName('v-filterselect-input'))
                .sendKeys(ENTER)
        entityType
                .shouldHave(value(type))
    }

    void setName(String nameValue) {
        nameField.shouldBe(EDITABLE)
                .setValue(nameValue)
    }

    CategoryAttributeEditor createAttribute() {
        createBtn.shouldBe(VISIBLE, ENABLED)
                .click()
        $j(CategoryAttributeEditor)
                .shouldBe(VISIBLE)
    }

    void editAttribute(String name) {
        categoryAttrsTable
                .shouldBe(VISIBLE)
                .selectRow(byCells(name))
                .shouldBe(VISIBLE)
        editBtn.shouldBe(ENABLED)
                .click()
        $j(CategoryAttributeEditor)
                .shouldBe(VISIBLE)
    }

    void checkCategoryAttribute(String attrName, String textValue) {
        categoryAttrsTable
                .shouldBe(VISIBLE)
                .getRow(byCells(attrName))
                .shouldBe(VISIBLE)
                .shouldHave(text(textValue))
    }

    void openLocalizationTab() {
        $j(TabSheet.class, 'tabSheet').with {
            TabSheet.Tab localizationTab = getTab('localizationTab')
            localizationTab.shouldBe(VISIBLE)
                    .select()
        }
    }

    void setLocalizedValue(String locale, String value) {
        tableLocalization
                .shouldBe(VISIBLE)
                .selectRow(byCells(locale))
                .doubleClick()
        $j('name')
                .shouldBe(VISIBLE)
                .setValue(value)
        $(byClassName('v-grid-editor-save'))
                .click()
    }
}
