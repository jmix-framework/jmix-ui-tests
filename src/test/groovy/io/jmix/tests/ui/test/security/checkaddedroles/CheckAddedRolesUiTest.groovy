package io.jmix.tests.ui.test.security.checkaddedroles

import com.codeborne.selenide.Condition
import io.jmix.masquerade.component.Button
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.browsers.AtmosphericGasBrowse
import io.jmix.tests.ui.screen.administration.security.editor.EntityAttributePolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.EntityPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.ResourceRoleEditor
import io.jmix.tests.ui.screen.administration.security.editor.RoleAssigmentEditor
import io.jmix.tests.ui.screen.administration.security.editor.RowLevelRoleEditor
import io.jmix.tests.ui.screen.administration.security.editor.ScreenPolicyEditor
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.security.BaseSecurityUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import static io.jmix.masquerade.Conditions.READONLY
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,security'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class CheckAddedRolesUiTest extends BaseSecurityUiTest {
    private static String ATMOSPHERIC_GAS_BROWSER_SCREEN_FULL_STRING = "Atmospheric gas browser (AtmosphericGas.browse)"
    private static String ATMOSPHERIC_GAS_BROWSER_MENU_ITEM_FULL_STRING = "Application > Atmospheric gas browser (AtmosphericGas.browse)"
    private static String ATMOSPHERIC_GAS_POLICY_GROUP = "AtmosphericGas"
    private static String USER_BROWSER_SCREEN_FULL_STRING = "Users (User.browse)"
    private static String USER_BROWSER_MENU_ITEM_FULL_STRING = "Application > Users (User.browse)"

    @Test
    @DisplayName("Checks user's permissions with added row level role with JPQL policy and resource role with entity/screen/menu/attributes policies")
    void checkAddedRowLevelRoleWithJPQLPolicy() {
        loginAsAdmin()
        openRowLevelRoleEditor()
        def rowRoleName = getUniqueName(ROW_LEVEL_ROLE)
        def rowRoleCode = getUniqueName(ROW_LEVEL_ROLE_CODE)
        createRowLevelRole(rowRoleName, rowRoleCode)
        openResourceRoleEditor()
        def resourceRoleName = getUniqueName(RESOURCE_ROLE)
        def resourceRoleCode = getUniqueName(RESOURCE_ROLE_CODE)
        $j(ResourceRoleEditor).with {
            nameField.setValue(resourceRoleName)
            codeField.setValue(resourceRoleCode)
            createResourceBtn.click(ENTITY_POLICY)
        }

        $j(EntityPolicyEditor).with {
            selectValueInComboBox(entityField, ATMOSPHERIC_GAS_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(ATMOSPHERIC_GAS_POLICY_GROUP))
            allActionsCheckBox.setChecked(true)
            clickButton(commit)
        }

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(SCREEN_POLICY)
        }
        $j(ScreenPolicyEditor).with {
            selectValueInComboBox(screenField, ATMOSPHERIC_GAS_BROWSER_SCREEN_FULL_STRING)
            menuItemField.shouldBe(READONLY)
            menuItemField.shouldHave(Condition.value(ATMOSPHERIC_GAS_BROWSER_MENU_ITEM_FULL_STRING))
            policyGroupField.shouldHave(Condition.value(ATMOSPHERIC_GAS_POLICY_GROUP))
            menuAccessField.setChecked(true)
            clickButton(commit)
        }
        $j(ResourceRoleEditor).with {
            createResourceBtn.click(ENTITY_ATTRIBUTE_POLICY)
        }
        $j(EntityAttributePolicyEditor).with {
            selectValueInComboBox(entityField, ATMOSPHERIC_GAS_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(ATMOSPHERIC_GAS_POLICY_GROUP))
            modify.setChecked(true)
            clickButton(ok)
        }
        $j(ResourceRoleEditor).with {
            clickButton(ok)
        }
        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).with {
            selectRowInTableByText(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            clickButton(showRoleAssignmentsBtn)
        }
        $j(RoleAssigmentEditor).with {
            clickButton(addResourceRoleBtn)
            selectRowInTableByText(resourceRoleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(resourceRoleName, RESOURCE_ROLE_ASSIGNMENTS_TABLE_JTEST_ID)

            clickButton(addRowLevelRoleBtn)
            selectRowInTableByText(rowRoleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(rowRoleName, ROW_LEVEL_ROLE_ASSIGNMENTS_TABLE_JTEST_ID)
            clickButton(commit)
        }

        logout()
        loginAsCustomUser(ACTIVE_USER_LOGIN, USER_PASSWORD)
        $j(MainScreen).openAtmosphericGasBrowse()
        $j(AtmosphericGasBrowse).with {
            checkRecordIsDisplayed("oxygen", "atmosphericGasesTable")
            checkRecordIsDisplayed("hydrogen", "atmosphericGasesTable")
            checkRecordIsNotDisplayed("methan", "atmosphericGasesTable")
            checkRecordIsNotDisplayed("helium", "atmosphericGasesTable")
        }
        logout()
        loginAsAdmin()
        $j(MainScreen).openResourceRoleBrowse()
        removeRole(resourceRoleName)
        $j(MainScreen).openRowLevelRoleBrowse()
        removeRole(rowRoleName)
    }

    @Test
    @DisplayName("Checks user's permissions with added row level role with predicate policy")
    void checkAddedRowLevelRoleWithPredicatePolicy() {
        loginAsAdmin()
        openRowLevelRoleEditor()
        def rowRoleName = getUniqueName(ROW_LEVEL_ROLE)
        def rowRoleCode = getUniqueName(ROW_LEVEL_ROLE_CODE)
        $j(RowLevelRoleEditor).with {
            nameField.setValue(rowRoleName)
            codeField.setValue(rowRoleCode)
        }

        addRowLevelPredicatePolicy()
        $j(RowLevelRoleEditor).with {
            checkRecordIsDisplayed(ROW_LEVEL_POLICY_PREDICATE_TYPE, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            clickButton(ok)
        }
        openResourceRoleEditor()
        def resourceRoleName = getUniqueName(RESOURCE_ROLE)
        def resourceRoleCode = getUniqueName(RESOURCE_ROLE_CODE)
        $j(ResourceRoleEditor).with {
            nameField.setValue(resourceRoleName)
            codeField.setValue(resourceRoleCode)
            createResourceBtn.click(ENTITY_POLICY)
        }

        $j(EntityPolicyEditor).with {
            selectValueInComboBox(entityField, USERS_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(USER_ENTITY_NAME))
            allActionsCheckBox.setChecked(true)
            clickButton(commit)
        }

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(SCREEN_POLICY)
        }
        $j(ScreenPolicyEditor).with {
            selectValueInComboBox(screenField, USER_BROWSER_SCREEN_FULL_STRING)
            menuItemField.shouldBe(READONLY)
            menuItemField.shouldHave(Condition.value(USER_BROWSER_MENU_ITEM_FULL_STRING))
            policyGroupField.shouldHave(Condition.value(USER_ENTITY_NAME))
            menuAccessField.setChecked(true)
            clickButton(commit)
        }
        $j(ResourceRoleEditor).with {
            createResourceBtn.click(ENTITY_ATTRIBUTE_POLICY)
        }
        $j(EntityAttributePolicyEditor).with {
            selectValueInComboBox(entityField, USERS_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(USER_ENTITY_NAME))
            modify.setChecked(true)
            clickButton(ok)
        }
        $j(ResourceRoleEditor).with {
            clickButton(ok)
        }
        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).with {
            selectRowInTableByText(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            clickButton(showRoleAssignmentsBtn)
        }
        $j(RoleAssigmentEditor).with {
            clickButton(addResourceRoleBtn)
            selectRowInTableByText(resourceRoleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(resourceRoleName, RESOURCE_ROLE_ASSIGNMENTS_TABLE_JTEST_ID)

            clickButton(addRowLevelRoleBtn)
            selectRowInTableByText(rowRoleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(rowRoleName, ROW_LEVEL_ROLE_ASSIGNMENTS_TABLE_JTEST_ID)
            clickButton(commit)
        }

        logout()
        loginAsCustomUser(ACTIVE_USER_LOGIN, USER_PASSWORD)
        $j(MainScreen).openUserBrowse()
        $j(AtmosphericGasBrowse).with {
            checkRecordIsDisplayed(ADMIN_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            checkRecordIsDisplayed(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            checkRecordIsNotDisplayed(INACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
        }
        logout()
        loginAsAdmin()
        $j(MainScreen).openResourceRoleBrowse()
        removeRole(resourceRoleName)
        $j(MainScreen).openRowLevelRoleBrowse()
        removeRole(rowRoleName)
    }
}
