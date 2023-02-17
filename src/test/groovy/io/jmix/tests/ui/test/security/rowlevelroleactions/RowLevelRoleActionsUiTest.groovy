package io.jmix.tests.ui.test.security.rowlevelroleactions

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextArea
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.security.browser.RoleBrowse
import io.jmix.tests.ui.screen.administration.security.editor.RowLevelPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.RowLevelRoleEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.UnsavedChangesDialog
import io.jmix.tests.ui.test.security.BaseSecurityUiTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,security'])
@ContextConfiguration(initializers = TestContextInitializer)
class RowLevelRoleActionsUiTest extends BaseSecurityUiTest {

    static void setNameAndCodeValuesAndSaveRole(String nameValue, String codeValue) {
        $j(RowLevelRoleEditor).with {
            nameField.setValue(nameValue)
            codeField.setValue(codeValue)
            clickButton(ok)
        }
    }

    static void interruptRoleCreating() {
        $j(RowLevelRoleEditor).with {
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    static void addChildAndSaveRole(String roleName, String childRoleName, String roleCode) {
        $j(RowLevelRoleEditor).with {
            nameField.setValue(roleName)
            codeField.setValue(roleCode)
            openChildRolesTab()
            clickButton($j(Button, ADD_BUTTON_JTEST_ID))
            selectRowInTableByText(childRoleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(childRoleName, CHILD_ROLES_TABLE_JTEST_ID)
            clickButton(ok)
        }
    }

    static void removeChildRole(String childRoleName) {
        $j(RowLevelRoleEditor).with {
            openChildRolesTab()
            selectRowInTableByText(childRoleName, CHILD_ROLES_TABLE_JTEST_ID)
            clickButton($j(Button, REMOVE_BUTTON_JTEST_ID))
            checkRecordIsNotDisplayed(childRoleName, CHILD_ROLES_TABLE_JTEST_ID)
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Creates, edits and removes a row level role")
    void editRowLevelRole() {
        loginAsAdmin()
        openRowLevelRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        setNameAndCodeValuesAndSaveRole(roleName, roleCode)

        $j(RoleBrowse).with {
            checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)
            selectRowInTableByText(roleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton(editBtn)
        }

        def roleName1 = getUniqueName(RESOURCE_ROLE)
        $j(RowLevelRoleEditor).with {
            nameField.setValue(roleName1)
            clickButton(ok)
        }

        $j(RoleBrowse).checkRecordIsDisplayed(roleName1, ROLE_MODELS_TABLE_JTEST_ID)

        removeRole(roleName1)
    }

    @Disabled("RowLevelPolicyModelEdit uses SourceCodeEditor that do not support setValue and some conditions")
    @Test
    @DisplayName("Adds a predicate policy to row level role")
    void addPredicatePolicy() {
        loginAsAdmin()
        openRowLevelRoleEditor()
        addRowLevelPredicatePolicy()
        $j(RowLevelRoleEditor).with {
            checkRecordIsDisplayed(USER_ENTITY_NAME, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            checkRecordIsDisplayed(ROW_LEVEL_POLICY_PREDICATE_TYPE, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
        }

        interruptRoleCreating()
    }

    @Disabled("RowLevelPolicyModelEdit uses SourceCodeEditor that do not support setValue and some conditions")
    @Test
    @DisplayName("Adds, edits and removes a JPQL policy of row level role")
    void editJPQLPolicy() {
        loginAsAdmin()
        openRowLevelRoleEditor()
        $j(RowLevelRoleEditor).with {
            clickButton(create)
        }
        $j(RowLevelPolicyEditor).with {
            actionField.shouldBe(READONLY)
            $j(TextArea, WHERE_CLAUSE_TEXTAREA_JTEST_ID).shouldBe(REQUIRED, EDITABLE, VISIBLE)

            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_ENTITY_AND_WHERE_CLAUSE_NOTIFICATION_CAPTION)

            selectValueInComboBox(typeField, ROW_LEVEL_POLICY_JPQL_TYPE)
            selectValueInComboBox(entityNameField, ATMOSPHERIC_GAS_FULL_STRING)

            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_WHERE_CLAUSE_NOTIFICATION_CAPTION)

            $j(TextArea, WHERE_CLAUSE_TEXTAREA_JTEST_ID).setValue(JPQL_CONDITION)
            clickButton(ok)
        }

        $j(RowLevelRoleEditor).with {
            checkRecordIsDisplayed(JPQL_CONDITION, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            selectRowInTableByText(JPQL_CONDITION, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(RowLevelPolicyEditor).with {
            $j(TextArea, WHERE_CLAUSE_TEXTAREA_JTEST_ID).setValue(JPQL_CONDITION_1)
            clickButton(ok)
        }
        $j(RowLevelRoleEditor).with {
            checkRecordIsDisplayed(JPQL_CONDITION_1, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            selectRowInTableByText(JPQL_CONDITION_1, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            clickButton(remove)
        }
        $j(ConfirmationDialog).confirmChanges()
        $j(RowLevelRoleEditor).checkRecordIsNotDisplayed(JPQL_CONDITION_1, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)

        interruptRoleCreating()
    }

    @Disabled("RowLevelPolicyModelEdit uses SourceCodeEditor that do not support setValue and some conditions")
    @Test
    @DisplayName("Adds a child role to row level role")
    void addChildRoleToResourceRole() {
        loginAsAdmin()
        openRowLevelRoleEditor()
        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)
        createRowLevelRole(roleName, roleCode)

        $j(RoleBrowse).with {
            checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton(createBtn)
        }
        def roleName1 = getUniqueName(RESOURCE_ROLE)
        def roleCode1 = getUniqueName(RESOURCE_ROLE_CODE)

        addChildAndSaveRole(roleName1, roleName, roleCode1)

        removeRole(roleName)
        removeRole(roleName1)
    }

    @Disabled("RowLevelPolicyModelEdit uses SourceCodeEditor that do not support setValue and some conditions")
    @Test
    @DisplayName("Removes a child role from row level role")
    void removeChildRoleFromResourceRole() {
        loginAsAdmin()
        openRowLevelRoleEditor()
        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)
        createRowLevelRole(roleName, roleCode)

        $j(RoleBrowse).with {
            checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton(createBtn)
        }
        def roleName1 = getUniqueName(RESOURCE_ROLE)
        def roleCode1 = getUniqueName(RESOURCE_ROLE_CODE)

        addChildAndSaveRole(roleName1, roleName, roleCode1)

        $j(RoleBrowse).with {
            selectRowInTableByText(roleName1, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton(editBtn)
        }
        removeChildRole(roleName)

        removeRole(roleName1)
        removeRole(roleName)
    }
}
