package io.jmix.tests.ui

import com.codeborne.selenide.Selenide
import io.jmix.tests.ui.screen.login.LoginScreen

import static io.jmix.masquerade.Components.wire

/**
 * Base class for UI tests in ui package
 */
abstract class BaseUiTest {

    /**
     * Login as Administrator
     */
    static void loginAsAdmin() {
        Selenide.open('/')
        wire(LoginScreen).loginAsAdmin()
    }

    /**
     * Login by given username and password
     *
     * @param username username
     * @param password user password
     */
    static void login(String username, String password) {
        Selenide.open('/')
        wire(LoginScreen).login(username, password)
    }
}
