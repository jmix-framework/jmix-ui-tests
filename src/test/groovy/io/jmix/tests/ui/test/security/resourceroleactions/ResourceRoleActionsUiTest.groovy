package io.jmix.tests.ui.test.security.resourceroleactions

import com.codeborne.selenide.Condition
import io.jmix.masquerade.component.Button
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.security.browser.RoleBrowse
import io.jmix.tests.ui.screen.administration.security.dialog.EntityResourcePolicyDialog
import io.jmix.tests.ui.screen.administration.security.editor.EntityAttributePolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.EntityPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.MenuPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.ResourceRoleEditor
import io.jmix.tests.ui.screen.administration.security.editor.ScreenPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.SpecificPolicyEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.dialog.UnsavedChangesDialog
import io.jmix.tests.ui.test.security.BaseSecurityUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Conditions.*

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,security'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class ResourceRoleActionsUiTest extends BaseSecurityUiTest {

    private static String ENTITY_INSPECTOR_MENU_ITEM_FULL_STRING = "Administration > Entity inspector (entityInspector.browse)"
    private static String ENTITY_INSPECTOR_SCREEN_KEY = "entityInspector.browse"
    private static String ENTITY_INSPECTOR_SCREEN_NAME = "Entity inspector"
    private static String ATMOSPHERE_BROWSER_SCREEN_FULL_STRING = "Atmosphere browser (Atmosphere.browse)"
    private static String ATMOSPHERE_BROWSER_MENU_ITEM_FULL_STRING = "Application > Atmosphere browser (Atmosphere.browse)"
    private static String ATMOSPHERE_BROWSER_SCREEN_KEY = "Atmosphere.browse"

    static void setNameAndCodeValuesAndSaveRole(String nameValue, String codeValue) {
        $j(ResourceRoleEditor).with {
            nameField.setValue(nameValue)
            codeField.setValue(codeValue)
            clickButton(ok)
        }
    }

    static void interruptRoleCreating() {
        $j(ResourceRoleEditor).with {
            clickButton(cancel)
        }

        $j(UnsavedChangesDialog).with {
            clickButton(doNotSave)
        }
    }

    static void addChildAndSaveRole(String roleName, String childRoleName, String roleCode) {
        $j(ResourceRoleEditor).with {
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
        $j(ResourceRoleEditor).with {
            openChildRolesTab()
            selectRowInTableByText(childRoleName, CHILD_ROLES_TABLE_JTEST_ID)
            clickButton($j(Button, REMOVE_BUTTON_JTEST_ID))
            checkRecordIsNotDisplayed(childRoleName, CHILD_ROLES_TABLE_JTEST_ID)
            clickButton(ok)
        }
    }

    @Test
    @DisplayName("Creates a UI resource role")
    void createResourceUIRole() {
        loginAsAdmin()
        openResourceRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        $j(ResourceRoleEditor).with {
            nameField.shouldBe(REQUIRED, VISIBLE, ENABLED)
            codeField.shouldBe(REQUIRED, VISIBLE, ENABLED)
        }
        setNameAndCodeValuesAndSaveRole(roleName, roleCode)
        $j(RoleBrowse).checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)

        removeRole(roleName)

    }

    @Test
    @DisplayName("Creates a role with non-unique code")
    void createRoleWithNonUniqueCode() {
        loginAsAdmin()
        openResourceRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        $j(ResourceRoleEditor).with {
            nameField.shouldBe(REQUIRED, VISIBLE, ENABLED)
            codeField.shouldBe(REQUIRED, VISIBLE, ENABLED)
        }
        setNameAndCodeValuesAndSaveRole(roleName, roleCode)
        $j(RoleBrowse).checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)

        $j(RoleBrowse).with {
            clickButton(createBtn)
        }

        setNameAndCodeValuesAndSaveRole(roleName, roleCode)

        $j(ResourceRoleEditor).with {
            checkNotification(ALERT_NOTIFICATION_CAPTION, NON_UNIQUE_CODE_NOTIFICATION_CAPTION)
        }
        interruptRoleCreating()

        removeRole(roleName)
    }

    @Test
    @DisplayName("Creates a database resource role")
    void createResourceDatabaseRole() {
        loginAsAdmin()
        openResourceRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        $j(ResourceRoleEditor).with {
            nameField.shouldBe(REQUIRED, VISIBLE, ENABLED)
            codeField.shouldBe(REQUIRED, VISIBLE, ENABLED)

            scopesField.select("API")
            scopesField.select("UI")
        }
        setNameAndCodeValuesAndSaveRole(roleName, roleCode)

        $j(RoleBrowse).checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)

        removeRole(roleName)
    }

    @Test
    @DisplayName("Edits a resource role")
    void editResourceRole() {
        loginAsAdmin()
        openResourceRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        $j(ResourceRoleEditor).with {
            nameField.shouldBe(REQUIRED, VISIBLE, ENABLED)
            codeField.shouldBe(REQUIRED, VISIBLE, ENABLED)
        }
        setNameAndCodeValuesAndSaveRole(roleName, roleCode)
        $j(RoleBrowse).checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)

        $j(RoleBrowse).with {
            selectRowInTableByText(roleName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton(editBtn)
        }
        def roleName1 = getUniqueName(RESOURCE_ROLE)
        $j(ResourceRoleEditor).with {
            nameField.setValue(roleName1)
            clickButton(ok)
        }
        $j(RoleBrowse).checkRecordIsDisplayed(roleName1, ROLE_MODELS_TABLE_JTEST_ID)

        removeRole(roleName1)
    }

    @Test
    @DisplayName("Removes a resource role")
    void removeResourceRole() {
        loginAsAdmin()
        openResourceRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        setNameAndCodeValuesAndSaveRole(roleName, roleCode)
        $j(RoleBrowse).checkRecordIsDisplayed(roleName, ROLE_MODELS_TABLE_JTEST_ID)

        removeRole(roleName)
    }

    @Test
    @DisplayName("Adds a menu policy to resource role")
    void addMenuPolicy() {
        loginAsAdmin()
        openResourceRoleEditor()

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(MENU_POLICY)
        }

        $j(MenuPolicyEditor).with {
            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_RESOURCE_NOTIFICATION_CAPTION)

            selectValueInComboBox(menuItemField, ENTITY_INSPECTOR_MENU_ITEM_FULL_STRING)

            screenField.shouldBe(READONLY)
            screenField.shouldHave(Condition.value(ENTITY_INSPECTOR_SCREEN_NAME))
            policyGroupField.setValue(TEST_GROUP_NAME)
            clickButton(commit)
        }
        $j(ResourceRoleEditor).checkRecordIsDisplayed(ENTITY_INSPECTOR_SCREEN_KEY, RESOURCE_POLICIES_TABLE_JTEST_ID)

        interruptRoleCreating()

    }

    @Test
    @DisplayName("Adds a screen policy to resource role")
    void addScreenPolicy() {
        loginAsAdmin()
        openResourceRoleEditor()

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(SCREEN_POLICY)
        }

        $j(ScreenPolicyEditor).with {
            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_RESOURCE_NOTIFICATION_CAPTION)

            selectValueInComboBox(screenField, ATMOSPHERE_BROWSER_SCREEN_FULL_STRING)

            menuItemField.shouldBe(READONLY)
            menuItemField.shouldHave(Condition.value(ATMOSPHERE_BROWSER_MENU_ITEM_FULL_STRING))
            policyGroupField.shouldHave(Condition.value(ATMOSPHERE_ENTITY_NAME))
            clickButton(commit)
        }
        $j(ResourceRoleEditor).checkRecordIsDisplayed(ATMOSPHERE_BROWSER_SCREEN_KEY, RESOURCE_POLICIES_TABLE_JTEST_ID)

        interruptRoleCreating()
    }

    @Test
    @DisplayName("Adds an entity policy to resource role")
    void addEntityPolicy() {
        loginAsAdmin()
        openResourceRoleEditor()

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(ENTITY_POLICY)
        }

        $j(EntityPolicyEditor).with {
            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_ENTITY_AND_ACTIONS_NOTIFICATION_CAPTION)

            selectValueInComboBox(entityField, ATMOSPHERE_FULL_STRING)

            policyGroupField.shouldHave(Condition.value(ATMOSPHERE_ENTITY_NAME))

            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_ACTIONS_NOTIFICATION_CAPTION)

            allActionsCheckBox.setChecked(true)

            clickButton(commit)
        }

        $j(ResourceRoleEditor).with {
            checkRecordIsDisplayed(CREATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)
            checkRecordIsDisplayed(READ_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)
            checkRecordIsDisplayed(UPDATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)
            checkRecordIsDisplayed(DELETE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)
        }

        interruptRoleCreating()

    }

    @Test
    @DisplayName("Adds an entity attribute policy to resource role")
    void addEntityAttributePolicy() {
        loginAsAdmin()
        openResourceRoleEditor()

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(ENTITY_ATTRIBUTE_POLICY)
        }

        $j(EntityAttributePolicyEditor).with {
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_ENTITY_NOTIFICATION_CAPTION)
            entityField.shouldBe(REQUIRED)
            selectValueInComboBox(entityField, ATMOSPHERE_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(ATMOSPHERE_ENTITY_NAME))
            modify.setChecked(true)

            clickButton(ok)
        }
        $j(ResourceRoleEditor).with {
            checkRecordIsDisplayed("Atmosphere.*", RESOURCE_POLICIES_TABLE_JTEST_ID)
            checkRecordIsDisplayed("entityAttribute", RESOURCE_POLICIES_TABLE_JTEST_ID)
        }

        interruptRoleCreating()
    }

    @Test
    @DisplayName("Adds a specific policy to resource role")
    void addSpecificPolicy() {
        loginAsAdmin()
        openResourceRoleEditor()

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(SPECIFIC_POLICY)
        }

        $j(SpecificPolicyEditor).with {
            clickButton(ok)
            checkNotification(ALERT_NOTIFICATION_CAPTION, REQUIRED_RESOURCE_NOTIFICATION_CAPTION)
            resourceField.shouldBe(ENABLED, REQUIRED)
            resourceField.setValue(RESOURCE_POLICY_NAME)
            policyGroupField.setValue(TEST_GROUP_NAME)
            clickButton(ok)
        }
        $j(ResourceRoleEditor).checkRecordIsDisplayed(RESOURCE_POLICY_NAME, RESOURCE_POLICIES_TABLE_JTEST_ID)

        interruptRoleCreating()
    }


    @Test
    @DisplayName("Edits an entity policy of resource role")
    void editEntityPolicy() {
        loginAsAdmin()
        openResourceRoleEditor()

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(ENTITY_POLICY)
        }

        $j(EntityPolicyEditor).with {
            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_ENTITY_AND_ACTIONS_NOTIFICATION_CAPTION)

            entityField.openOptionsPopup()
                    .select(ATMOSPHERE_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(ATMOSPHERE_ENTITY_NAME))

            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_ACTIONS_NOTIFICATION_CAPTION)

            createCheckBox.setChecked(true)

            clickButton(commit)
        }
        $j(ResourceRoleEditor).checkRecordIsDisplayed(CREATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)

        $j(ResourceRoleEditor).with {
            selectRowInTableByText(CREATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)
            clickButton(edit)
        }

        $j(EntityResourcePolicyDialog).with {
            entityField.shouldHave(value(ATMOSPHERE_FULL_STRING))
            actionField.shouldHave(value(CREATE_ACTION))
            policyGroupField.shouldHave(value(ATMOSPHERE_ENTITY_NAME))

            actionField.openOptionsPopup()
                    .select(READ_ACTION)
            clickButton(ok)
        }
        $j(ResourceRoleEditor).checkRecordIsDisplayed(READ_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)

        interruptRoleCreating()
    }

    @Test
    @DisplayName("Removes an entity policy from resource role")
    void removeEntityPolicy() {
        loginAsAdmin()
        openResourceRoleEditor()

        $j(ResourceRoleEditor).with {
            createResourceBtn.click(ENTITY_POLICY)
        }

        $j(EntityPolicyEditor).with {
            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_ENTITY_AND_ACTIONS_NOTIFICATION_CAPTION)

            entityField.openOptionsPopup()
                    .select(ATMOSPHERE_FULL_STRING)
            policyGroupField.shouldHave(Condition.value(ATMOSPHERE_ENTITY_NAME))

            clickButton(commit)
            checkNotification(ALERT_NOTIFICATION_CAPTION, EMPTY_ACTIONS_NOTIFICATION_CAPTION)

            createCheckBox.setChecked(true)

            clickButton(commit)
        }
        $j(ResourceRoleEditor).checkRecordIsDisplayed(CREATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)

        $j(ResourceRoleEditor).with {
            selectRowInTableByText(CREATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)
            clickButton(remove)
        }

        $j(ConfirmationDialog).confirm()

        $j(ResourceRoleEditor).checkRecordIsNotDisplayed(CREATE_ACTION, RESOURCE_POLICIES_TABLE_JTEST_ID)

        interruptRoleCreating()
    }

    @Test
    @DisplayName("Adds a child role to resource role")
    void addChildRoleToResourceRole() {
        loginAsAdmin()
        openResourceRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        createResourceRole(roleName, roleCode)

        $j(RoleBrowse).with {
            clickButton(createBtn)
        }
        def roleName1 = getUniqueName(RESOURCE_ROLE)
        def roleCode1 = getUniqueName(RESOURCE_ROLE_CODE)
        addChildAndSaveRole(roleName1, roleName, roleCode1)

        removeRole(roleName1)
        removeRole(roleName)
    }

    @Test
    @DisplayName("Removes a child role from resource role")
    void removeChildRoleFromResourceRole() {
        loginAsAdmin()
        openResourceRoleEditor()

        def roleName = getUniqueName(RESOURCE_ROLE)
        def roleCode = getUniqueName(RESOURCE_ROLE_CODE)

        createResourceRole(roleName, roleCode)

        $j(RoleBrowse).with {
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
