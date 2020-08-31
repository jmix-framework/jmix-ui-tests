package io.jmix.tests.ui.screen.main


import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.SideMenu
import io.jmix.tests.ui.screen.login.LoginScreen

import static io.jmix.masquerade.Components.wire

class MainScreen extends Composite<MainScreen> {

    @Wire
    Button logoutButton
    @Wire
    SideMenu sideMenu

    LoginScreen logout() {
        logoutButton.click()
        wire(LoginScreen)
    }
}
