package io.jmix.tests.ui.test.reports.editreport

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextArea
import io.jmix.masquerade.component.TextField
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.system.dialog.UnsavedChangesDialog
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.REQUIRED
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Conditions.VISIBLE

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,reports'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class ReportDatasetActionUiTest extends BaseReportUiTest {

    public static final String DATASET_NAME = "set"
    public static final String DATASET_TABLE_J_TEST_ID = "dataSetsTable_composition"
    public static final String DATASET_NAME_J_TEST_ID = "name"
    public static final String DATASET_CREATE_BUTTON_J_TEST_ID = "create"
    public static final String COMBOBOX_ENTITY_J_TEST_ID = "entityParamField"
    public static final String COMBOBOX_ENTITIES_LIST_J_TEST_ID = "entitiesParamField"
    public static final String JSON_SOURCE_TYPES_J_TEST_ID = "jsonSourceTypeField"
    public static final String JSON_TEXTAREA_J_TEST_ID = "jsonPathQueryTextAreaField"

    public static final String DATASET_SQL_OPTION = "SQL"
    public static final String DATASET_JPQL_OPTION = "JPQL"
    public static final String DATASET_ENTITY_OPTION = "Entity"
    public static final String DATASET_ENTITIES_LIST_OPTION = "List of entities"
    public static final String DATASET_JSON_OPTION = "JSON"

    static void selectDatasetType(String type) {
        $j(ComboBox, COMBOBOX_DATASETS_TYPE_JTEST_ID)
                .openOptionsPopup()
                .select(type)
    }

    static void checkComboboxVisibility(String jTestId) {
        $j(ComboBox, jTestId)
                .shouldBe(VISIBLE)
    }

    static void checkFetchPlanButtonIsEnabled() {
        $j(Button, FETCH_PLAN_EDIT_BUTTON_JTEST_ID)
                .shouldBe(VISIBLE)
                .shouldBe(ENABLED)
    }

    @Test
    @DisplayName("Checks available dataset types for report")
    void checkAvailableDatasetsInReport() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()
        $j(ReportEditor).with {
            clickButton($j(Button, DATASET_TABLE_J_TEST_ID, DATASET_CREATE_BUTTON_J_TEST_ID))
            $j(TextField, DATASET_TABLE_J_TEST_ID, DATASET_NAME_J_TEST_ID)
                    .shouldBe(VISIBLE)
                    .setValue(DATASET_NAME)
            checkComboboxVisibility(COMBOBOX_DATASETS_TYPE_JTEST_ID)

            // select SQL type
            selectDatasetType(DATASET_SQL_OPTION)
            checkComboboxVisibility(COMBOBOX_DATASTORES_JTEST_ID)

            // select JPQL type
            selectDatasetType(DATASET_JPQL_OPTION)
            checkComboboxVisibility(COMBOBOX_DATASTORES_JTEST_ID)

            // select Entity type
            selectDatasetType(DATASET_ENTITY_OPTION)
            checkComboboxVisibility(COMBOBOX_ENTITY_J_TEST_ID)
            checkFetchPlanButtonIsEnabled()

            // select List of entities type
            selectDatasetType(DATASET_ENTITIES_LIST_OPTION)
            checkComboboxVisibility(COMBOBOX_ENTITIES_LIST_J_TEST_ID)
            checkFetchPlanButtonIsEnabled()

            // select JSON type
            selectDatasetType(DATASET_JSON_OPTION)
            checkComboboxVisibility(JSON_SOURCE_TYPES_J_TEST_ID)

            $j(TextArea, JSON_TEXTAREA_J_TEST_ID)
                    .shouldBe(VISIBLE)
                    .shouldBe(REQUIRED)
                    .shouldBe(ENABLED)
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }
}
