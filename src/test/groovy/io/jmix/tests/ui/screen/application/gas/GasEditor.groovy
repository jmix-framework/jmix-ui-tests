package io.jmix.tests.ui.screen.application.gas

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.*
import io.jmix.tests.ui.screen.system.dialog.OptionDialog
import io.jmix.tests.ui.screen.system.dialog.UnsavedChangesDialog

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static com.codeborne.selenide.Condition.cssValue
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

class GasEditor extends Composite<GasEditor> {
    @Wire(path = '+GasSecond')
    TextField second

    @Wire(path = '+GasBoolean')
    CheckBox booleanAttribute

    @Wire(path = '+GasDate')
    DateField dateAttr

    @Wire(path = '+GasDate')
    ComboBox dateAttrCollection

    @Wire(path = '+GasDateNoTime')
    DateField dateWithoutTimeAttr

    @Wire(path = '+GasDateNoTime')
    ComboBox dateWithoutTimeCollection

    @Wire(path = '+GasDouble')
    TextField doubleAttribute

    @Wire(path = '+GasEnumer')
    ComboBox enumerCollection

    @Wire(path = '+GasDouble')
    public ComboBox doubleAttributeCollection

    @Wire(path = '+GasUser')
    public ComboBox userCollection

    @Wire(path = '+GasFixedPoint')
    TextField fixedPoint

    @Wire(path = '+GasFixedPoint')
    ComboBox fixedPointCollection

    @Wire(path = '+GasInteger')
    TextField integerField

    @Wire(path = '+GasInteger')
    ComboBox integerCollection

    @Wire(path = '+GasString')
    TextArea stringField

    @Wire(path = '+GasString')
    ComboBox stringCollection

    @Wire(path = '+GasCalculated')
    public ComboBox calculatedAttribute

    @Wire
    TextField nameField

    @Wire
    Button closeBtn

    @Wire
    Button commitAndCloseBtn

    void saveChanges() {
        commitAndCloseBtn
                .shouldBe(VISIBLE)
                .click()
    }

    void close() {
        closeBtn
                .shouldBe(VISIBLE)
                .click()
    }

    void closeWithoutSaving() {
        closeBtn.shouldBe(VISIBLE)
                .click()
        $j(UnsavedChangesDialog)
                .shouldBe(VISIBLE)
                .doNotSave
                .shouldBe(VISIBLE)
                .click()
    }

    def static getDate(int NumberOfDays) {
        def df = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        def date = LocalDate.now().minusDays(NumberOfDays)
        def formattedDate = date.format(df)
    }


    def static getTime() {
        def df = DateTimeFormatter.ofPattern("HH:mm")
        def date = LocalDate.now()
        def formattedDate = date.format(df)
    }

    void checkAttributeValidation() {
        saveChanges()
        $j(Notification.class)
                .shouldBe(VISIBLE)
    }

    void setName(String nameValue) {
        nameField
                .shouldBe(VISIBLE)
                .setValue(nameValue)
    }

    void checkIntegerWidth(String widthValue){
        integerField
                .shouldBe(VISIBLE)
                .shouldHave(cssValue('width', widthValue))
    }

}
