package io.jmix.tests.ui.screen.login

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.components.Button
import io.jmix.masquerade.components.ComboBox
import io.jmix.masquerade.components.PasswordField
import io.jmix.masquerade.components.TextField
import io.jmix.tests.ui.screen.main.MainScreen

import static io.jmix.masquerade.Components.wire

class LoginScreen extends Composite<LoginScreen> {

    @Wire
    TextField usernameField

    @Wire
    PasswordField passwordField

    @Wire
    Button loginButton

    @Wire
    ComboBox localesField

    MainScreen defaultLogin() {
        submit()
    }

    MainScreen login(String username, String password) {
        usernameField.value = username
        passwordField.value = password
        submit()
    }

    MainScreen submit() {
        loginButton.click()
        wire(MainScreen)
    }
}
