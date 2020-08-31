package io.jmix.tests.ui.screen.login

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.PasswordField
import io.jmix.masquerade.component.TextField

class LoginScreen extends Composite<LoginScreen> {

    @Wire
    TextField usernameField

    @Wire
    PasswordField passwordField

    @Wire
    Button loginButton

    @Wire
    ComboBox localesField

    void loginAsAdmin() {
        submit()
    }

    void login(String username, String password) {
        usernameField.value = username
        passwordField.value = password
        submit()
    }

    void submit() {
        loginButton.click()
    }
}
