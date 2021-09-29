package io.jmix.tests.ui.test.reports.editreport

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.reports.editor.ReportParameterEditor
import io.jmix.tests.ui.screen.system.dialog.UnsavedChangesDialog
import io.jmix.tests.ui.screen.reports.editor.ReportEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.test.reports.BaseReportUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,reports'])
@ContextConfiguration(initializers = TestContextInitializer)
class ReportParameterActionUiTest extends BaseReportUiTest {

    public static final String STRING_PARAM = "String"
    public static final String NUMBER_PARAM = "Number"
    public static final String BOOLEAN_PARAM = "Boolean"
    public static final String DATE_PARAM = "Date"
    public static final String TIME_PARAM = "Time"
    public static final String DATETIME_PARAM = "Date and time"
    public static final String ENUM_PARAM = "Enumeration"
    public static final String ENTITY_PARAM = "Entity"
    public static final String ENTITIES_LIST_PARAM = "List of entities"

    public static final String ENUM_FIELD_J_TEST_ID = "enumerationField"
    public static final String ENTITY_FIELD_J_TEST_ID = "metaClassField"
    public static final String ENTITY_SCREEN_FIELD_J_TEST_ID = "screenField"

    public static final String ENUM_VALUE = "CustomerGrade (CustomerGrade)"
    public static final String SCREEN_VALUE = "Company browser (Company.browse)"


    @Test
    @DisplayName("Creates report parameter with simple types")
    void createReportParameterSimpleTypes() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
        }

        def params = [STRING_PARAM, NUMBER_PARAM, BOOLEAN_PARAM, DATE_PARAM, DATETIME_PARAM, TIME_PARAM]
        for (int i = 0; i < params.size(); i++) {
            $j(ReportEditor).with {
                clickButton($j(Button, PARAMETER_CREATE_BUTTON_JTEST_ID))
            }

            $j(ReportParameterEditor).with {
                name.setValue(params[i])
                alias.setValue(params[i].substring(0, 4).toLowerCase().concat(i as String))
                parameterTypeField.openOptionsPopup().select(params[i])
                clickButton(ok)
            }
            $j(ReportEditor).with {
                checkRecordIsDisplayed(params[i], PARAMETERS_TABLE_JTEST_ID)
            }
        }

        $j(ReportEditor).with {
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    @Test
    @DisplayName("Creates report parameter with Enumeration type")
    void createReportParameterEnum() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
            clickButton($j(Button, PARAMETER_CREATE_BUTTON_JTEST_ID))
        }

        $j(ReportParameterEditor).with {
            name.setValue(ENUM_PARAM)
            alias.setValue(ENUM_PARAM)
            parameterTypeField.openOptionsPopup().select(ENUM_PARAM)
            $j(ComboBox, ENUM_FIELD_J_TEST_ID).openOptionsPopup().select(ENUM_VALUE)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(ENUM_PARAM, PARAMETERS_TABLE_JTEST_ID)
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    @Test
    @DisplayName("Creates report parameter with Entity and Entities List types")
    void createReportParameterEntityAndEntitiesList() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
        }

        def params = [ENTITY_PARAM, ENTITIES_LIST_PARAM]
        for (int i = 0; i < params.size(); i++) {

            $j(ReportEditor).with {
                clickButton($j(Button, PARAMETER_CREATE_BUTTON_JTEST_ID))
            }
            $j(ReportParameterEditor).with {
                name.setValue(params[i])
                alias.setValue(params[i].substring(0, 4).toLowerCase().concat(i as String))
                parameterTypeField.openOptionsPopup().select(params[i])
                $j(ComboBox, ENTITY_FIELD_J_TEST_ID).openOptionsPopup().select(COMPANY_FULL_STRING)
                $j(ComboBox, ENTITY_SCREEN_FIELD_J_TEST_ID).openOptionsPopup().select(SCREEN_VALUE)
                clickButton(ok)
            }
            $j(ReportEditor).with {
                checkRecordIsDisplayed(params[i], PARAMETERS_TABLE_JTEST_ID)
            }
        }

        $j(ReportEditor).with {
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    @Test
    @DisplayName("Edits report parameter")
    void editReportParameter() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
            clickButton($j(Button, PARAMETER_CREATE_BUTTON_JTEST_ID))
        }

        $j(ReportParameterEditor).with {
            name.setValue(COMPANY_NAME)
            alias.setValue(COMPANY_NAME)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_NAME, PARAMETERS_TABLE_JTEST_ID)
            selectRowInTableByText(COMPANY_NAME, PARAMETERS_TABLE_JTEST_ID)
            clickButton($j(Button, PARAMETER_EDIT_BUTTON_JTEST_ID))
        }

        $j(ReportParameterEditor).with {
            name.setValue(COMPANY_GRADE)
            alias.setValue(COMPANY_GRADE)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_GRADE, PARAMETERS_TABLE_JTEST_ID)
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    @Test
    @DisplayName("Removes report parameter")
    void removeReportParameter() {
        loginAsAdmin()
        maximizeWindowSize()
        openReportEditor()

        $j(ReportEditor).with {
            openTab(PARAMETERS_AND_FORMATS_JTEST_ID)
            clickButton($j(Button, PARAMETER_CREATE_BUTTON_JTEST_ID))
        }

        $j(ReportParameterEditor).with {
            name.setValue(COMPANY_NAME)
            alias.setValue(COMPANY_NAME)
            clickButton(ok)
        }

        $j(ReportEditor).with {
            checkRecordIsDisplayed(COMPANY_NAME, PARAMETERS_TABLE_JTEST_ID)
            selectRowInTableByText(COMPANY_NAME, PARAMETERS_TABLE_JTEST_ID)
            clickButton($j(Button, PARAMETER_REMOVE_BUTTON_JTEST_ID))
        }

        $j(ConfirmationDialog).with {
            clickButton(yes)
        }

        $j(ReportEditor).with {
            checkRecordIsNotDisplayed(COMPANY_NAME, PARAMETERS_TABLE_JTEST_ID)
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }
}
