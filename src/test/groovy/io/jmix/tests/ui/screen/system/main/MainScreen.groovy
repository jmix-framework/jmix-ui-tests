package io.jmix.tests.ui.screen.system.main

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.SideMenu
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.company.CompanyBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportBrowse
import io.jmix.tests.ui.screen.reports.browser.ReportGroupBrowse
import io.jmix.tests.ui.screen.reports.screen.ReportRunScreen
import io.jmix.tests.ui.screen.reports.screen.ShowReportTableScreen
import io.jmix.tests.ui.screen.system.login.LoginScreen

import static io.jmix.masquerade.Components.wire
import static io.jmix.tests.ui.menu.Menus.*

class MainScreen extends Composite<MainScreen> {

    @Wire
    Button logoutButton
    @Wire
    SideMenu sideMenu

    LoginScreen logout() {
        logoutButton.click()
        wire(LoginScreen)
    }

    EntityInspectorBrowse openEntityInspectorBrowse() {
        sideMenu.openItem(ENTITY_INSPECTOR_BROWSE)
    }

    UserBrowse openUserBrowse() {
        sideMenu.openItem(USER_BROWSE)
    }

    ReportBrowse openReportsBrowse() {
        sideMenu.openItem(REPORTS_BROWSE)
    }

    ReportGroupBrowse openReportsGroupBrowse() {
        sideMenu.openItem(REPORTS_GROUP_BROWSE)
    }

    ReportRunScreen openReportsRunScreen() {
        sideMenu.openItem(REPORTS_RUN_SCREEN)
    }

    CompanyBrowse openCompanyBrowse(){
        sideMenu.openItem(COMPANY_BROWSE)
    }

    ShowReportTableScreen openReportsShowTablesScreen(){
        sideMenu.openItem(REPORTS_SHOW_TABLES_SCREEN)
    }
}
