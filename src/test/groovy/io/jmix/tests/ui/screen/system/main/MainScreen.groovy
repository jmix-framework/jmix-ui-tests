package io.jmix.tests.ui.screen.system.main


import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.SideMenu
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.login.LoginScreen

import static io.jmix.masquerade.Components.wire
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.tests.ui.menu.Menus.ENTITY_INSPECTOR_BROWSE
import static io.jmix.tests.ui.menu.Menus.USER_BROWSE

class MainScreen extends Composite<MainScreen> {

    @Wire
    Button logoutButton
    @Wire
    SideMenu sideMenu

    LoginScreen logout() {
        logoutButton.click()
        wire(LoginScreen)
    }

    EntityInspectorBrowse openEntityInspectorBrowse(){
        sideMenu.openItem(ENTITY_INSPECTOR_BROWSE)
    }

    UserBrowse openUserBrowse(){
        sideMenu.openItem(USER_BROWSE)
    }
}
