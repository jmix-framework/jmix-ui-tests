package io.jmix.tests.ui.test

import com.codeborne.selenide.Selenide
import io.jmix.tests.ui.screen.system.login.LoginScreen
import io.jmix.tests.ui.screen.system.main.MainScreen

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
     * Logout
     */
    static void logout() {
        $j(MainScreen).logout()
    }
}
