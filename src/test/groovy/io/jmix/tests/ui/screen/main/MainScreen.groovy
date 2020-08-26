package io.jmix.tests.ui.screen.main


import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.components.Button
import io.jmix.tests.ui.screen.login.LoginScreen

import static io.jmix.masquerade.Components.wire

class MainScreen extends Composite<MainScreen> {

    @Wire
    Button logoutButton

    LoginScreen logout() {
        logoutButton.click()
        wire(LoginScreen)
    }
}
