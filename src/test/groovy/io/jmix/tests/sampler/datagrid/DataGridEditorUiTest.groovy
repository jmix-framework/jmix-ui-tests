package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.DataGrid
import io.jmix.masquerade.component.TextField
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
import static org.openqa.selenium.Keys.ESCAPE

@ExtendWith(ChromeExtension)
class DataGridEditorUiTest extends BaseSamplerUiTest {

    public static final String NEW_NAME = "NewName"
    public static final String NEW_LAST_NAME = "NewLastName"
    public static final String HIGH_GRADE = "High"
    public static final String NEW_AGE = "30"

    @Test
    @DisplayName("Check dataGrid inline editor while editorBuffered is on")
    void checkDataGridCInlineEditorWhileEditorBufferedOn() {
        openSample('datagrid-editor')
        setEditorBuffered(true)
        $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .getCell(byRowColIndexes(0, 0))
                .doubleClick()
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
                .shouldBe(VISIBLE)
                .click()
        $j(DataGrid.class, 'customersDataGrid')
                .getRow(byCells(NEW_NAME, NEW_LAST_NAME, NEW_AGE, HIGH_GRADE))
                .shouldBe(VISIBLE)
    }

    @Test
    @DisplayName("Check dataGrid inline editor while editorBuffered is off")
    void checkDataGridCInlineEditorWhileEditorBufferedOff() {
        openSample('datagrid-editor')
        setEditorBuffered(false)
        $j(DataGrid.class, 'customersDataGrid')
                .sort('column_name', ASCENDING)
                .getCell(byRowColIndexes(0, 0))
                .doubleClick()
        $j(TextField.class, 'name')
                .shouldBe(EDITABLE)
                .setValue(NEW_NAME)
        $j(TextField.class, 'lastName')
                .shouldBe(EDITABLE)
                .setValue(NEW_LAST_NAME)
        $j(TextField.class, 'age')
                .shouldBe(EDITABLE)
                .setValue(NEW_AGE)
        $j(CheckBox.class, 'active')
                .shouldBe(EDITABLE)
                .setChecked(false)
                .setChecked(false)
        $j(ComboBox.class, 'grade')
                .openOptionsPopup()
                .select(HIGH_GRADE)
        $j(TextField.class, 'name')
                .delegate
                .sendKeys(ESCAPE)
        $j(DataGrid.class, 'customersDataGrid')
                .getRow(byCells(NEW_NAME, NEW_LAST_NAME, NEW_AGE, HIGH_GRADE))
                .shouldBe(VISIBLE)
    }

    private void setEditorBuffered(boolean editorBuffered) {
        $j(CheckBox.class, 'editorBuffered')
                .shouldBe(EDITABLE)
                .setChecked(editorBuffered)
    }
}
