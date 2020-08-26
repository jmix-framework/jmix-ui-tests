package io.jmix.tests.ui

import io.jmix.tests.BaseTest
import io.jmix.tests.ui.screen.login.LoginScreen

import static io.jmix.masquerade.Components.wire

/**
 * Base class for UI tests in ui package
 */
abstract class BaseUiTest extends BaseTest {

    /**
     * Login as Administrator
     */
    static void defaultLogin() {
        wire(LoginScreen).defaultLogin()
    }

    /**
     * Login by given username and password
     *
     * @param username username
     * @param password user password
     */
    static void login(String username, String password) {
        wire(LoginScreen).login(username, password)
    }
}
