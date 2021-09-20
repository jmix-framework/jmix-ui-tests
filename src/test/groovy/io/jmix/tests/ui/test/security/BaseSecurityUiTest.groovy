package io.jmix.tests.ui.test.security

import com.codeborne.selenide.Condition
import io.jmix.masquerade.component.TextArea
import io.jmix.tests.ui.screen.administration.security.browser.RoleBrowse
import io.jmix.tests.ui.screen.administration.security.editor.EntityPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.ResourceRoleEditor
import io.jmix.tests.ui.screen.administration.security.editor.RowLevelPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.RowLevelRoleEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import io.jmix.tests.ui.test.utils.UiHelper

import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.REQUIRED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

class BaseSecurityUiTest extends BaseUiTest implements UiHelper {

    public static final String USER_BROWSER_TABLE_JTEST_ID = "usersTable_composition"
    public static final String ROLE_MODELS_TABLE_JTEST_ID = "roleModelsTable"
    public static final String RESOURCE_ROLE_ASSIGNMENTS_TABLE_JTEST_ID = "resourceRoleAssignmentsTable"
    public static final String ROW_LEVEL_ROLE_ASSIGNMENTS_TABLE_JTEST_ID = "rowLevelRoleAssignmentsTable"
    public static final String RESOURCE_POLICIES_TABLE_JTEST_ID = "resourcePoliciesTable"
    public static final String ROW_LEVEL_POLICIES_TABLE_JTEST_ID = "rowLevelPoliciesTable"
    public static final String CHILD_ROLES_TABLE_JTEST_ID = "childRolesTable"
    public static final String ADD_BUTTON_JTEST_ID = "add"
    public static final String REMOVE_BUTTON_JTEST_ID = "remove"
    public static final String LOOKUP_BUTTON_JTEST_ID = "lookupSelectAction"
    public static final String SCRIPT_TEXTAREA_JTEST_ID = "scriptField"
    public static final String WHERE_CLAUSE_TEXTAREA_JTEST_ID = "whereClauseField"

    public static final String ATMOSPHERE_FULL_STRING = "Atmosphere (Atmosphere)"
    public static final String ATMOSPHERIC_GAS_FULL_STRING = "Atmospheric gas (AtmosphericGas)"
    public static final String USERS_FULL_STRING = "User (User)"
    public static final String ATMOSPHERE_ENTITY_NAME = "Atmosphere"
    public static final String USER_ENTITY_NAME = "User"

    public static final String MENU_POLICY = "Menu policy"
    public static final String SCREEN_POLICY = "Screen policy"
    public static final String SPECIFIC_POLICY = "Specific policy"
    public static final String ENTITY_POLICY = "Entity policy"
    public static final String ENTITY_ATTRIBUTE_POLICY = "Entity attribute policy"

    public static final String RESOURCE_ROLE = "Resource role"
    public static final String RESOURCE_ROLE_CODE = "resourcerole"
    public static final String ROW_LEVEL_ROLE = "Row level role"
    public static final String ROW_LEVEL_ROLE_CODE = "rowlevelrole"
    public static final String ROW_LEVEL_POLICY_JPQL_TYPE = "JPQL"
    public static final String ROW_LEVEL_POLICY_PREDICATE_TYPE = "Predicate"

    public static final String RESOURCE_POLICY_NAME = "ui.loginToUi"
    public static final String TEST_GROUP_NAME = "test"
    public static final String CREATE_ACTION = "create"
    public static final String READ_ACTION = "read"
    public static final String UPDATE_ACTION = "update"
    public static final String DELETE_ACTION = "delete"

    public static final String INACTIVE_USER_LOGIN = "inactiveUser"
    public static final String ACTIVE_USER_LOGIN = "activeUser"
    public static final String ADMIN_USER_LOGIN = "admin"
    public static final String USER_PASSWORD = "qwerty"
    public static final String NEW_PASSWORD = "qwerty1"

    public static final String JPQL_CONDITION = "{E}.volume > 15"
    public static final String JPQL_CONDITION_1 = "{E}.volume > 20"
    public static final String PREDICATE_CONDITION = "{E}.enabled"

    public static final String ALERT_NOTIFICATION_CAPTION = "Alert"
    public static final String EMPTY_RESOURCE_NOTIFICATION_CAPTION = "Resource"
    public static final String REQUIRED_RESOURCE_NOTIFICATION_CAPTION = "Resource required"
    public static final String REQUIRED_NAME_AND_CODE_NOTIFICATION_CAPTION = "Name required\nCode required"
    public static final String EMPTY_ENTITY_AND_ACTIONS_NOTIFICATION_CAPTION = "Select entity\nSelect actions"
    public static final String REQUIRED_ENTITY_AND_WHERE_CLAUSE_NOTIFICATION_CAPTION = "Entity name required\nWhere clause required"
    public static final String REQUIRED_WHERE_CLAUSE_NOTIFICATION_CAPTION = "Where clause required"
    public static final String EMPTY_ENTITY_NOTIFICATION_CAPTION = "Select entity"
    public static final String EMPTY_ACTIONS_NOTIFICATION_CAPTION = "Select actions"
    public static final String NON_UNIQUE_CODE_NOTIFICATION_CAPTION = "Code must be unique"

    static String getUniqueName(String base) {
        String generatedString = getGeneratedString()
        return base + generatedString
    }

    static void removeRole(String roleName) {
        $j(RoleBrowse).with {
            selectRowInTableByText(roleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton(removeBtn)
        }
        $j(ConfirmationDialog).with {
            clickButton(yes)
        }
        $j(RoleBrowse).with {
            checkRecordIsNotDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)
        }
    }

    static void openResourceRoleEditor() {
        $j(MainScreen).with {
            openResourceRoleBrowse()
        }

        $j(RoleBrowse).with {
            clickButton(createBtn)
        }
    }

    static void openRowLevelRoleEditor() {
        $j(MainScreen).with {
            openRowLevelRoleBrowse()
        }

        $j(RoleBrowse).with {
            clickButton(createBtn)
        }
    }

    static void createResourceRole(String roleName, String roleCode) {
        $j(ResourceRoleEditor).with {
            nameField.setValue(roleName)
            codeField.setValue(roleCode)
            createResourceBtn.click(ENTITY_POLICY)
        }

        $j(EntityPolicyEditor).with {
            selectValueInComboBox(entityField, ATMOSPHERE_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(ATMOSPHERE_ENTITY_NAME))
            createCheckBox.setChecked(true)

            clickButton(commit)
        }

        $j(ResourceRoleEditor).with {
            checkRecordIsDisplayed(CREATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)
            clickButton(ok)
        }
    }

    static void addRowLevelJPQLPolicy() {
        $j(RowLevelRoleEditor).with {
            clickButton(create)
        }
        $j(RowLevelPolicyEditor).with {
            selectValueInComboBox(typeField, ROW_LEVEL_POLICY_JPQL_TYPE)
            selectValueInComboBox(entityNameField, ATMOSPHERIC_GAS_FULL_STRING)
            $j(TextArea, WHERE_CLAUSE_TEXTAREA_JTEST_ID).setValue(JPQL_CONDITION)
            clickButton(ok)
        }
    }

    static void createRowLevelRole(String roleName, String roleCode) {
        $j(RowLevelRoleEditor).with {
            nameField.setValue(roleName)
            codeField.setValue(roleCode)
        }

        addRowLevelJPQLPolicy()
        $j(RowLevelRoleEditor).with {
            checkRecordIsDisplayed(JPQL_CONDITION, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            clickButton(ok)
        }
    }

    static void addRowLevelPredicatePolicy() {
        $j(RowLevelRoleEditor).with {
            clickButton(create)
        }
        $j(RowLevelPolicyEditor).with {
            selectValueInComboBox(typeField, ROW_LEVEL_POLICY_PREDICATE_TYPE)
            $j(TextArea, SCRIPT_TEXTAREA_JTEST_ID).shouldBe(REQUIRED, EDITABLE, VISIBLE)
            selectValueInComboBox(entityNameField, USERS_FULL_STRING)
            $j(TextArea, SCRIPT_TEXTAREA_JTEST_ID).setValue(PREDICATE_CONDITION)

            clickButton(ok)
        }
    }

}
