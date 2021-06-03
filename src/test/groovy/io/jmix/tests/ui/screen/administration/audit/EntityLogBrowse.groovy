package io.jmix.tests.ui.screen.administration.audit

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.*
import io.jmix.tests.ui.screen.application.individual.IndividualBrowse

import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*
import static io.jmix.tests.ui.test.audit.EntityLogViewTest.*
import static org.openqa.selenium.Keys.ENTER

class EntityLogBrowse extends Composite<EntityLogBrowse> {

    public static final String APPLIED_CHANGES = 'Changes have been applied'

    @Wire
    Button create

    @Wire
    Button edit

    @Wire
    ComboBox entityNameField

    @Wire
    CheckBox autoCheckBox

    @Wire
    CheckBox manualCheckBox

    @Wire
    CheckBox selectAllCheckBox

    @Wire(path = 'removeBtn')
    Button remove

    @Wire(path = 'saveBtn')
    Button save

    @Wire
    Table loggedEntityTable

    @Wire(path = 'reloadBtn')
    Button applyChanges

    @Wire(path = 'userField')
    ComboBox user

    @Wire(path = 'filterEntityNameField')
    ComboBox entity

    @Wire(path = 'changeTypeField')
    ComboBox changeType

    @Wire(path = 'instancePicker')
    EntityPicker instance

    @Wire
    DateField fromDateField

    @Wire
    DateField tillDateField

    @Wire(path = 'searchBtn')
    Button search

    @Wire(path = 'clearEntityLogTableBtn')
    Button clear

    @Wire
    Table entityLogTable

    @Wire(path = 'entityLogAttrTable')
    Table attrTable

    void openSetupTab() {
        $j(TabSheet.class, 'tabsheet').with {
            TabSheet.Tab setupTab = getTab('setup')
            setupTab.shouldBe(VISIBLE).select()
        }
    }

    void createSetup() {
        create
                .shouldBe(ENABLED)
                .click()
    }

    void saveSetup() {
        save.shouldBe(VISIBLE, ENABLED)
                .click()
    }

    void applyChanges() {
        applyChanges
                .shouldBe(ENABLED)
                .click()
    }

    void checkAppliedChangesNotification() {
        $j(Notification.class)
                .shouldHave(caption(APPLIED_CHANGES))
    }

    void selectSetupRecord(String recordName) {
        loggedEntityTable
                .shouldBe(VISIBLE)
                .selectRow((byCells(recordName)))
                .shouldBe(VISIBLE)
    }

    void setCheckbox(CheckBox checkbox, boolean value) {
        checkbox
                .shouldBe(VISIBLE)
                .setChecked(value)
        value ? checkbox.shouldBe(CHECKED) : checkbox.shouldNotBe(CHECKED)
    }

    void selectNameFromDropdown(String name) {
        entityNameField
                .shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(byText(name))
                .shouldHave(value(name))
    }

    void checkAttrValue(String attrNAme, String attrValue) {
        attrTable.should(VISIBLE)
                .getRow(byCells(attrNAme, attrValue))
                .shouldBe(VISIBLE)
    }

    void clickSearch() {
        search.shouldBe(VISIBLE)
                .click()
    }

    void clickClear() {
        clear.shouldBe(VISIBLE)
                .click()
    }

    void selectLoggedEntity(String changeType, String entity) {
        entityLogTable
                .shouldBe(VISIBLE)
                .selectRow(byCells(changeType, entity))
                .shouldBe(VISIBLE)
    }

    void setChangeType(String changeTypeVal) {
        changeType.shouldBe(ENABLED)
                .openOptionsPopup()
                .select(changeTypeVal)
    }

    //todo
    //https://github.com/Haulmont/jmix-masquerade/issues/12
    void setUserValue(String value) {
        $j('userField').setValue(value)
        $(byClassName('c-suggestionfield-item')).click()
    }

    void checkRecordDoesNotDisplay(String changeType, String entity) {
        entityLogTable
                .shouldBe(VISIBLE)
                .getRow(byCells(changeType, entity))
                .shouldNotBe(VISIBLE)
    }

    void setInstance(String name) {
        instance.shouldBe(ENABLED)
        $(byJTestId('lookup')).click()
        $j(IndividualBrowse).with {
            individualsTable.shouldBe(VISIBLE)
                    .selectRow(byCells(name))
            lookupSelectAction.shouldBe(VISIBLE)
                    .click()
            instance.shouldHave(value(name))
        }
    }

    void setEntity(String entityName) {
        entity.shouldBe(EDITABLE)
                .setFilter(entityName)
                .delegate
                .$(byClassName('v-filterselect-input'))
                .sendKeys(ENTER)
    }

    void checkIndividualRecord(String changeType = CREATE,
                               String firstName = IVAN,
                               String lastName = IVANOV) {
        $j(EntityLogBrowse).with {
            selectLoggedEntity(changeType, firstName)
            checkAttrValue('First name', firstName)
            checkAttrValue('Last name', lastName)
        }
    }
}
