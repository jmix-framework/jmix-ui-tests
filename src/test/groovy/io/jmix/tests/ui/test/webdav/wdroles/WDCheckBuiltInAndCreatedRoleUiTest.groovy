package io.jmix.tests.ui.test.webdav.wdroles

import com.codeborne.selenide.WebDriverRunner
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextArea
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.security.browser.RoleBrowse
import io.jmix.tests.ui.screen.administration.security.editor.RoleAssigmentEditor
import io.jmix.tests.ui.screen.administration.security.editor.RowLevelPolicyEditor
import io.jmix.tests.ui.screen.administration.security.editor.RowLevelRoleEditor
import io.jmix.tests.ui.screen.administration.webdav.browse.WebDAVDocumentBrowse
import io.jmix.tests.ui.screen.administration.webdav.dialog.DocumentVersionDialog
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.webdav.WebDAVBaseUITest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.Keys
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Selectors.byCssSelector
import static io.jmix.masquerade.Conditions.DISABLED
import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.REQUIRED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

@Disabled("RowLevelPolicyModelEdit uses SourceCodeEditor that do not support setValue and some conditions")
@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,webdav'])
@ContextConfiguration(initializers = TestContextInitializer)
class WDCheckBuiltInAndCreatedRoleUiTest extends WebDAVBaseUITest {

    public static final String ROW_LEVEL_POLICY_PREDICATE_TYPE = "Predicate"
    public static final String SCRIPT_TEXTAREA_JTEST_ID = "scriptField"
    public static final String ROW_LEVEL_POLICIES_TABLE_JTEST_ID = "rowLevelPoliciesTable"
    public static final String ACTIVE_USER_LOGIN = "activeUser"
    public static final String USER_BROWSER_TABLE_JTEST_ID = "usersTable_composition"
    public static final String ROLE_MODELS_TABLE_JTEST_ID = "roleModelsTable"
    public static final String RESOURCE_ROLE_ASSIGNMENTS_TABLE_JTEST_ID = "resourceRoleAssignmentsTable"
    public static final String ROW_LEVEL_ROLE_ASSIGNMENTS_TABLE_JTEST_ID = "rowLevelRoleAssignmentsTable"
    public static final String LOOKUP_BUTTON_JTEST_ID = "lookupSelectAction"
    public static final String USER_PASSWORD = "qwerty"
    public static final String WEBDAV_MIN_ACCESS_ROLE_NAME = "WebDAV: minimal access"
    public static final String WEBDAV_VIEW_DOCUMENT_BROWSER_ROLE_NAME = "WebDAV: view document browser"

    @Test
    @DisplayName("Checks user access with built-in WD resource roles and created row level role with predicate policy")
    void checkUserAccess() {
        def firstFileName = getUniqueName(BASE_FILENAME)
        def firstFilePath = RESOURCES_PATH + firstFileName
        def firstFile = createNewFile(BASE_FILE_PATH, firstFilePath)

        def secondFileName = getUniqueName(BASE_FILENAME)
        def secondFilePath = RESOURCES_PATH + secondFileName
        def secondFile = createNewFile(BASE_FILE_PATH, secondFilePath)

        loginAsAdmin()
        $j(MainScreen).openWebDAVDocumentBrowse()
        $j(WebDAVDocumentBrowse).with {
            uploadNewDocument(uploadBtn, firstFile)
            checkRecordIsDisplayed(firstFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)

            uploadNewDocument(uploadBtn, secondFile)
            checkRecordIsDisplayed(secondFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
        }

        $j(MainScreen).openRowLevelRoleBrowse()

        $j(RoleBrowse).with {
            clickButton(createBtn)
        }
        def rowRoleUniqueName = getUniqueName("RowLevelRestrictAccess")
        def rowRoleUniqueCode = getUniqueName("rowLevelRestrictAccess")

        $j(RowLevelRoleEditor).with {
            fillTextField(nameField, rowRoleUniqueName)
            fillTextField(codeField, rowRoleUniqueCode)
            clickButton(create)

            $j(RowLevelPolicyEditor).with {
                selectValueInComboBox(typeField, ROW_LEVEL_POLICY_PREDICATE_TYPE)
                $j(TextArea, SCRIPT_TEXTAREA_JTEST_ID).shouldBe(REQUIRED, EDITABLE, VISIBLE)
                selectValueInComboBox(entityNameField, "Webdav document (webdav_WebdavDocument)")
                selectValueInComboBox(actionField, "Update")
                $j(TextArea, SCRIPT_TEXTAREA_JTEST_ID).setValue("import io.jmix.core.security.CurrentAuthentication\n" +
                        "\n" +
                        "def authBean = applicationContext.getBean(CurrentAuthentication)\n" +
                        "\n" +
                        "return {E}.createdBy.equals(authBean.user.username)")

                clickButton(ok)
            }
            checkRecordIsDisplayed("webdav_WebdavDocument", ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            checkRecordIsDisplayed(ROW_LEVEL_POLICY_PREDICATE_TYPE, ROW_LEVEL_POLICIES_TABLE_JTEST_ID)
            clickButton(ok)
        }

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).with {
            selectRowInTableByText(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            clickButton(showRoleAssignmentsBtn)
        }
        $j(RoleAssigmentEditor).with {
            clickButton(addResourceRoleBtn)
            WebDriverRunner.webDriver.findElement(byCssSelector("body")).sendKeys(Keys.CONTROL, Keys.END)
            selectRowInTableByText(WEBDAV_MIN_ACCESS_ROLE_NAME, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(WEBDAV_MIN_ACCESS_ROLE_NAME, RESOURCE_ROLE_ASSIGNMENTS_TABLE_JTEST_ID)

            clickButton(addResourceRoleBtn)
            WebDriverRunner.webDriver.findElement(byCssSelector("body")).sendKeys(Keys.CONTROL, Keys.END)
            selectRowInTableByText(WEBDAV_VIEW_DOCUMENT_BROWSER_ROLE_NAME, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(WEBDAV_VIEW_DOCUMENT_BROWSER_ROLE_NAME, RESOURCE_ROLE_ASSIGNMENTS_TABLE_JTEST_ID)

            clickButton(addRowLevelRoleBtn)
            selectRowInTableByText(rowRoleUniqueName, ROLE_MODELS_TABLE_JTEST_ID)
            clickButton($j(Button, LOOKUP_BUTTON_JTEST_ID))
            checkRecordIsDisplayed(rowRoleUniqueName, ROW_LEVEL_ROLE_ASSIGNMENTS_TABLE_JTEST_ID)
            clickButton(commit)
        }

        logout()
        loginAsCustomUser(ACTIVE_USER_LOGIN, USER_PASSWORD)

        $j(MainScreen).openWebDAVDocumentBrowse()
        $j(WebDAVDocumentBrowse).with {
            checkRecordIsDisplayed(firstFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            checkRecordIsDisplayed(secondFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            selectRowInTableByText(secondFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(disableVersioningBtn)
            checkNotification("Access denied")
            selectRowInTableByText(firstFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(manageVersionsBtn)

            $j(DocumentVersionDialog).with {
                ok.shouldBe(VISIBLE, DISABLED)
                clickButton(closeBtn)
            }
        }

        logout()
        loginAsAdmin()

        $j(MainScreen).openWebDAVDocumentBrowse()

        // removing WebDAV documents from db
        $j(WebDAVDocumentBrowse).with {
            selectRowInTableByText(secondFileName, WEBDAV_DOCUMENTS_TABLE_J_TEST_ID)
            clickButton(lockBtn)
            clickButton(removeBtn)
            $j(ConfirmationDialog).confirmChanges()
        }

        // removing created files in `resource` directory
        cleanTempFile(firstFilePath)
        cleanTempFile(secondFilePath)
    }
}
