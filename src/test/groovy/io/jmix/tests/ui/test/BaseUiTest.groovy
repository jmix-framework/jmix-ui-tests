package io.jmix.tests.ui.test

import com.codeborne.selenide.Selenide
import io.jmix.tests.ui.screen.system.login.LoginScreen
import io.jmix.tests.ui.screen.system.main.MainScreen
import org.junit.jupiter.api.AfterEach

import static io.jmix.masquerade.Selectors.$j

/**
 * Base class for UI tests in ui package
 */
abstract class BaseUiTest {

    /**
     * Login as Administrator
     */
    static void loginAsAdmin() {
        Selenide.open('/')
        $j(LoginScreen).loginAsAdmin()
    }

    /**
     * Login as Administrator with Russian locale
     */
    static void loginAsAdminRus() {
        Selenide.open('/')
        $j(LoginScreen).loginWithLocale('Russian')
    }

    /**
     * Login as custom user
     */
    static void loginAsCustomUser(String login, String password) {
        Selenide.open('/')
        $j(LoginScreen).login(login, password)
    }

    /**
     * Logout after each test
     */
    @AfterEach
    void logout() {
        $j(MainScreen).logout()
    }
}
