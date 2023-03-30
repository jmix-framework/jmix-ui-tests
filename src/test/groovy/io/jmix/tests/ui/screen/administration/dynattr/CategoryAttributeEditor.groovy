package io.jmix.tests.ui.screen.administration.dynattr

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.*
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.test_support.component.checkbox.UiCheckBox

import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*
import static io.jmix.tests.ui.test.dynattr.TargetScreenActionsTest.*
import static org.openqa.selenium.Keys.ENTER
import static org.openqa.selenium.Keys.TAB

class CategoryAttributeEditor extends Composite<CategoryAttributeEditor> {
    public static final String BOOLEAN = 'Boolean'
    public static final String DOUBLE = 'Double'
    public static final String DATE = 'Date'
    public static final String DATE_NO_TIME_TYPE = 'Date without time'
    public static final String DATE_WITHOUT_TIME = 'DateNoTime'
    public static final String BOOLEAN_VALIDATE_SCRIPT = 'if (value != false) return "the value \'\\${value}\' is incorrect"'
    public static final String FALSE = "False"
    public static final String DEFAULT_DATE_VALUE = "12/12/2020"
    public static final String DEFAULT_DOUBLE = '50.1'
    public static final String USER = 'User'
    public static final String ENTITY_TYPE = 'Entity'
    public static final String ADMIN = 'admin'
    public static final String ADMIN_VALUE = '[admin]'
    public static final String ENUMER = "Enumer"
    public static final String ENUMERATION_TYPE = "Enumeration"
    public static final String RED = "Red"
    public static final String GREEN = "Green"
    public static final String BLUE = "Blue"
    public static final String LIST_OF_ENUM_VALUES = "Red,Blue"
    public static final String FIXED_POINT = "FixedPoint"
    public static final String FIXED_POINT_NUMBER = "Fixed-point number"
    public static final String NUMBER_FORMAT = "##,##,##,##,##"
    public static final String DEFAULT_FIXED_POINT_VALUE = "1234567890"
    public static final String FORMATTED_VALUE = "12,34,56,78,90"
    public static final String INTEGER = "Integer"
    public static final String DEFAULT_INT_VALUE = '15'
    public static final String INTEGER_CODE = 'GasInteger'
    public static final String STRING = "String"
    public static final String DEFAULT_STRING_VALUE = "Default string"
    public static final String BOOLEAN_VALUE = 'Bool'

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'nameField'])
    TextField nameField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'codeField'])
    TextField codeField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'descriptionField'])
    TextArea descriptionField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'requiredField'])
    UiCheckBox requiredField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'dataTypeField'])
    ComboBox type

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'validationScriptField'])
    TextArea validationScript

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'lookupField'])
    CheckBox lookupField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'isCollectionField'])
    UiCheckBox isCollectionField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'widthField'])
    TextField widthField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'rowsCountField'])
    TextField rowsCount

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultStringField'])
    TextField defaultStringValue

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultDateWithoutTimeField'])
    DateField defaultDateWithoutTimeField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultIntField'])
    TextField defaultIntField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultBooleanField'])
    ComboBox defaultBooleanField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'windowCommitAndClose'])
    Button ok

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'dependsOnAttributesField'])
    ComboBox AttributeDependsOn

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'optionsLoaderTypeField'])
    ComboBox optionsType

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'recalculationScriptField'])
    TextArea recalculationScript

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'tabSheet'])
    TabSheet tabSheet

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'createBtn'])
    Button addTargetScreen

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'removeBtn'])
    Button removeTargetScreen

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'targetScreensTable'])
    Table targetScreensTable

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultDateField'])
    DateField defaultDateField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultDateIsCurrentField'])
    UiCheckBox defaultDateIsCurrent

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'minDoubleField'])
    TextField minDoubleField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'maxDoubleField'])
    TextField maxDoubleField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultDoubleField'])
    TextField defaultDoubleField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'entityClassField'])
    ComboBox entityClassField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'screenField'])
    ComboBox screenField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultEntityIdField'])
    ComboBox defaultEntityIdField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'minDecimalField'])
    TextField minValue

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'maxDecimalField'])
    TextField maxValue

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'defaultDecimalField'])
    TextField defaultValue

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'numberFormatPatternField'])
    TextField numberFormatPattern

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'enumerationField'])
    TextField enumerationField

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'editEnumerationBtn'])
    Button editEnumerationBtn

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'minIntField'])
    TextField minIntValue

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'maxIntField'])
    TextField maxIntValue

    @Wire(path = ['dialog_dynat_CategoryAttribute.edit', 'whereClauseField'])
    TextField whereClause

    @Wire
    TextField localizedNameField

    @Wire
    TextField localizedDescriptionField

    @Wire(path = 'localizedValuesDataGrid')
    DataGrid localizedTable


    void saveChanges() {
        ok.shouldBe(VISIBLE)
                .click()
    }

    void setName(String name) {
        nameField
                .shouldBe(VISIBLE, EDITABLE, REQUIRED)
                .setValue(name)
                .shouldHave(value(name))
                .delegate
                .sendKeys(TAB)
    }

    void checkCode(String categoryName, String attrName) {
        codeField
                .shouldBe(EDITABLE, REQUIRED)
                .shouldHave(value(categoryName + attrName))
    }

    void setRequired(boolean isRequired) {
        requiredField.shouldBe(ENABLED)
                .setChecked(isRequired)

    }

    void setDateIsCurrent(boolean value) {
        defaultDateIsCurrent.shouldBe(VISIBLE)
                .setChecked(value)
                .setChecked(value)
    }

    void setType(String attrType) {
        type.shouldBe(ENABLED)
                .openOptionsPopup()
                .select(attrType)
                .shouldHave(value(attrType))
    }

    void setValidationScript(String scriptValue) {
        validationScript
                .shouldBe(VISIBLE)
                .delegate
                .$(byClassName('ace_text-input'))
                .setValue(scriptValue)
    }

    void openVisibilityTab() {
        openTab('visibilityTab')
    }

    void openCalculatedValuesAndOptionsTab() {
        openTab('calculatedValuesAndOptionsTab')
    }

    void openLocalizationTab() {
        TabSheet.Tab tab = tabSheet.getTab('localizationTab')
        tab.shouldBe(VISIBLE)
                .select()
    }

    void openGeneralTab() {
        openTab('mainTab')
    }

    void openTab(String id) {
        $j(TabSheet.class, 'dialog_dynat_CategoryAttribute.edit','tabSheet').with {
            TabSheet.Tab tab = getTab(id)
            tab.shouldBe(VISIBLE)
                    .select()
        }
    }

    def selectScreen(int rowIndex) {
        targetScreensTable.selectRow(byIndex(rowIndex))
                .$(byJTestId('screen'))
                .$(byClassName('v-filterselect-input'))
    }

    void setScreen(int rowIndex, String screenName, String componentValue = null) {
        selectScreen(rowIndex)
                .setValue(screenName)
                .sendKeys(ENTER)
        targetScreensTable.selectRow(byIndex(rowIndex))
                .$(byJTestId(COMPONENT))
                .setValue((String) componentValue)
    }

    void addTargetScreen() {
        addTargetScreen
                .shouldBe(ENABLED)
                .click()
    }

    void setDefaultBooleanValue(String value) {
        defaultBooleanField
                .shouldBe(VISIBLE, ENABLED)
                .openOptionsPopup()
                .select(value as String)
    }

    void addScreensOnVisibility(String form = null, String editorName = GAS_EDITOR, String browserName = GAS_BROWSER ) {
        openVisibilityTab()
        addTargetScreen()
        setScreen(0, editorName, form)
        addTargetScreen()
        setScreen(1, browserName, null)
    }

    void addGasEditorOnVisibility(int rowIndex, String form = null, String editorName = GAS_EDITOR) {
        addTargetScreen()
        setScreen(rowIndex, editorName, form)
    }

    void removeScreensOnVisibility(int index) {
        selectScreen(index)
        removeTargetScreen.shouldBe(ENABLED)
                .click()
        $j(ConfirmationDialog).confirmChanges()
    }

    void setDefaultDateField(String dateValue) {
        defaultDateField
                .shouldBe(VISIBLE)
                .setDateValue(dateValue)
    }

    void setDefaultDateWithoutTimeField(String dateValue) {
        defaultDateWithoutTimeField
                .shouldBe(VISIBLE)
                .setDateValue(dateValue)
    }


    void setIsCollection(boolean isTrue) {
        isCollectionField
                .shouldBe(VISIBLE)
                .setChecked(isTrue)
    }

    void setDefaultDouble(def doubleValue) {
        defaultDoubleField.shouldBe(VISIBLE)
                .setValue(doubleValue)
    }

    void setIncorrectDefaultDouble(def value) {
        setDefaultDouble(value)
        saveChanges()
        $j(Notification.class)
                .shouldBe(VISIBLE)
        clearFieldValue(defaultDoubleField)
    }

    void clearFieldValue(def fieldName) {
        fieldName
                .delegate
                .clear()
    }

    void checkDynamicAttributeValidation() {
        saveChanges()
        $j(Notification.class)
                .shouldBe(VISIBLE)
    }

    EnumerationEditor editEnum() {
        editEnumerationBtn
                .shouldBe(VISIBLE)
                .click()
        $j(EnumerationEditor)
                .shouldBe(VISIBLE)
    }

    void setWhereClauseForCalculatedJpql(String scriptValue) {
        openCalculatedValuesAndOptionsTab()
        def whereClauseElement = whereClause
                .delegate
                .$(byClassName('ace_text-input'))
        optionsType.shouldBe(VISIBLE)
                .setValue("JPQL")
        whereClauseElement
                .clear()
        whereClauseElement
                .setValue(scriptValue)
    }

    void setDescription(String value) {
        descriptionField
                .shouldBe(VISIBLE)
                .setValue(value)
    }

    void setLocalizedValue(String locale, String nameValue, String descriptionValue) {
        localizedTable
                .shouldBe(VISIBLE)
                .selectRow(byCells(locale))
                .doubleClick()
        $j('name')
                .shouldBe(VISIBLE)
                .setValue(nameValue)
        $j('description')
                .shouldBe(VISIBLE)
                .setValue(descriptionValue)
        $(byClassName('v-grid-editor-save'))
                .click()
    }
}
