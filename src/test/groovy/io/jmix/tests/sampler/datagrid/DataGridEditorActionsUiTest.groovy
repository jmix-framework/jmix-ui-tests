package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.*
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Selenide.$x
import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*
import static io.jmix.masquerade.component.DataGrid.SortDirection.ASCENDING

@ExtendWith(ChromeExtension)
class DataGridEditorActionsUiTest extends BaseSamplerUiTest {

    public static final String NEW_NAME = "NewName"
    public static final String NEW_LAST_NAME = "NewLastName"
    public static final String HIGH_GRADE = "High"
    public static final String NEW_AGE = "30"

    @Test
    @DisplayName("Check dataGrid overridden edit action")
    void checkDataGridOverriddenEditAction() {
        openSample('datagrid-editor-actions')
        $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .selectRow(byIndex(0))
        $j(Button.class, 'editBtn')
                .click()
        $j(TextField.class, 'name')
                .shouldBe(EDITABLE)
                .setValue(NEW_NAME)
        $j(TextField.class, 'lastName')
                .shouldBe(EDITABLE)
                .setValue(NEW_LAST_NAME)
        $j(TextField.class, 'age')
                .shouldBe(EDITABLE)
                .setValue(NEW_AGE)
        //TODO https://github.com/Haulmont/jmix-masquerade/issues/6
        $j(CheckBox.class, 'active')
                .shouldBe(EDITABLE)
                .setChecked(false)
                .setChecked(false)
        $j(ComboBox.class, 'grade')
                .openOptionsPopup()
                .select(HIGH_GRADE)
        $x("//*[contains(@class, 'v-grid-editor-save')]")
                .click()
        $j(DataGrid.class, 'customersDataGrid')
                .getRow(byCells(NEW_NAME, NEW_LAST_NAME, NEW_AGE, HIGH_GRADE))
                .shouldBe(VISIBLE)
    }

    @Test
    @DisplayName("Check dataGrid overridden create action")
    void checkDataGridOverriddenCreateAction() {
        openSample('datagrid-editor-actions')
        $j(Button.class, 'createBtn')
                .click()
        $x("//*[contains(@class, 'v-customcomponent')]//*[@j-test-id='name']")
                .setValue(NEW_NAME)
        $x("//*[contains(@class, 'v-customcomponent')]//*[@j-test-id='lastName']")
                .setValue(NEW_LAST_NAME)
        $x("//*[contains(@class, 'v-customcomponent')]//*[@j-test-id='age']")
                .setValue(NEW_AGE)
        $x("//*[contains(@class, 'v-grid-editor-save')]")
                .click()
        $j(DataGrid.class, 'customersDataGrid')
                .getRow(byCells(NEW_NAME, NEW_LAST_NAME, NEW_AGE))
                .shouldBe(VISIBLE)
    }
}
