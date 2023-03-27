package io.jmix.tests.ui.test.security.useractions

import com.codeborne.selenide.Selenide
import io.jmix.masquerade.component.Notification
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.screen.administration.security.dialog.ChangePasswordDialog
import io.jmix.tests.ui.screen.administration.security.dialog.ResetTokensDialog
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.login.LoginScreen
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.security.BaseSecurityUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,security'])
class UserActionsUiTest extends BaseSecurityUiTest {

    private final String URL_STRING = "/"
    private final String ALERT_NOTIFICATION_CAPTION = "Alert"
    private final String PASSWD_SUCCESS_CHANGED_NOTIFICATION_CAPTION = "Password successfully changed"
    private final String PASSWD_DONT_MATCH_NOTIFICATION_CAPTION = "Passwords don't match"
    private final String CONF_PASSWD_REQUIRED_NOTIFICATION_DESCRIPTION = "Confirmed password can not be blank"
    private final String PASSWDS_REQUIRED_NOTIFICATION_DESCRIPTION =
            "Password can not be blank" + "\n" + CONF_PASSWD_REQUIRED_NOTIFICATION_DESCRIPTION
    private final String TOKENS_REMOVED_NOTIFICATION_CAPTION = "'Remember me' tokens have been removed"
    private final String INACTIVE_USER_NOTIFICATION_CAPTION = "Login failed"
    private final String INACTIVE_USER_NOTIFICATION_DESCRIPTION = "Unknown login name or bad password"

    private final String CHANGE_PASSWORD = "Change password"
    private final String RESET_REMEMBER_ME_TOKEN = "Reset 'remember me' tokens"

    @Test
    @DisplayName("Changes a user's password")
    void changeUserPassword() {
        loginAsAdmin()
        $j(MainScreen).openUserBrowse()

        $j(UserBrowse).with {
            checkRecordIsDisplayed(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            selectRowInTableByText(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            additionalActionsBtn.click(CHANGE_PASSWORD)
        }
        $j(ChangePasswordDialog).with {
            clickButton(okBtn)
            checkNotification(ALERT_NOTIFICATION_CAPTION, PASSWDS_REQUIRED_NOTIFICATION_DESCRIPTION)
            passwordField.setValue(NEW_PASSWORD)
            clickButton(okBtn)
            checkNotification(ALERT_NOTIFICATION_CAPTION, CONF_PASSWD_REQUIRED_NOTIFICATION_DESCRIPTION)

            confirmPasswordField.setValue(USER_PASSWORD)
            clickButton(okBtn)
            checkNotification(ALERT_NOTIFICATION_CAPTION, PASSWD_DONT_MATCH_NOTIFICATION_CAPTION)

            confirmPasswordField.setValue(NEW_PASSWORD)
            clickButton(okBtn)
        }
        checkNotification(PASSWD_SUCCESS_CHANGED_NOTIFICATION_CAPTION)
        logout()
        $j(LoginScreen).with {
            login(ACTIVE_USER_LOGIN, NEW_PASSWORD)
        }
        logout()
        $j(LoginScreen).loginAsAdmin()
        $j(MainScreen).openUserBrowse()

        $j(UserBrowse).with {
            checkRecordIsDisplayed(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            selectRowInTableByText(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            additionalActionsBtn.click(CHANGE_PASSWORD)
        }
        $j(ChangePasswordDialog).with {
            passwordField.setValue(USER_PASSWORD)
            confirmPasswordField.setValue(USER_PASSWORD)
            clickButton(okBtn)
        }
        checkNotification(PASSWD_SUCCESS_CHANGED_NOTIFICATION_CAPTION)
    }

    @Test
    @DisplayName("Resets 'remember me' token")
    void resetRememberMeToken() {
        Selenide.open(URL_STRING)

        $j(LoginScreen).with {
            rememberMeCheckBox.setChecked(true)
            login(ACTIVE_USER_LOGIN, USER_PASSWORD)
        }

        $j(MainScreen).shouldBe(VISIBLE)
        $j(Notification).shouldNotBe(VISIBLE)
        logout()
        loginAsAdmin()
        $j(MainScreen).openUserBrowse()

        $j(UserBrowse).with {
            selectRowInTableByText(ACTIVE_USER_LOGIN, USER_BROWSER_TABLE_JTEST_ID)
            additionalActionsBtn.click(RESET_REMEMBER_ME_TOKEN)
        }
        $j(ResetTokensDialog).with {
            clickButton(forSelected)
        }
        checkNotification(TOKENS_REMOVED_NOTIFICATION_CAPTION)
    }

    @Test
    @DisplayName("Logins as an inactive user")
    void loginAsInactiveUser() {
        loginAsCustomUser(INACTIVE_USER_LOGIN, USER_PASSWORD)
        checkNotification(INACTIVE_USER_NOTIFICATION_CAPTION, INACTIVE_USER_NOTIFICATION_DESCRIPTION)
        loginAsCustomUser(ADMIN_USER_LOGIN, ADMIN_USER_LOGIN)
    }
}
