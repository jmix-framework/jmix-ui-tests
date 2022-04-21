package io.jmix.tests.ui.screen.system.main

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.SideMenu
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.administration.datatools.browsers.AtmosphericGasBrowse
import io.jmix.tests.ui.screen.administration.emailtemplates.browse.EmailTemplateBrowse
import io.jmix.tests.ui.screen.administration.security.browser.RoleBrowse
import io.jmix.tests.ui.screen.administration.dynattr.DynamicAttributeBrowse
import io.jmix.tests.ui.screen.administration.webdav.browse.WebDAVDocumentBrowse
import io.jmix.tests.ui.screen.application.company.CompanyBrowse
import io.jmix.tests.ui.screen.application.discounts.DiscountBrowser
import io.jmix.tests.ui.screen.application.dymanicCategories.DynamicCategoryBrowse
import io.jmix.tests.ui.screen.application.gas.GasBrowse
import io.jmix.tests.ui.screen.application.localizedDynamic.LocalizedDynamicBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.application.wddisabledentity.WDDisabledEntityBrowse
import io.jmix.tests.ui.screen.application.wdenabledentity.WDEnabledEntityBrowse
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

    AtmosphericGasBrowse openAtmosphericGasBrowse() {
        sideMenu.openItem(ATMOSPHERIC_GAS_BROWSE)
    }

    RoleBrowse openResourceRoleBrowse() {
        sideMenu.openItem(RESOURCE_ROLE_BROWSE)
    }

    RoleBrowse openRowLevelRoleBrowse() {
        sideMenu.openItem(ROW_LEVEL_ROLE_BROWSE)
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

    CompanyBrowse openCompanyBrowse() {
        sideMenu.openItem(COMPANY_BROWSE)
    }

    ShowReportTableScreen openReportsShowTablesScreen() {
        sideMenu.openItem(REPORTS_SHOW_TABLES_SCREEN)
    }

    DynamicAttributeBrowse openDynamicAttributeBrowse() {
        sideMenu.openItem(DYNAMIC_ATTRIBUTES_BROWSE)
    }

    GasBrowse openGasBrowse() {
        sideMenu.openItem(GAS_BROWSE)
    }

    DynamicCategoryBrowse openDynamicCategoryBrowse() {
        sideMenu.openItem(DYNAMIC_CATEGORY)
    }

    LocalizedDynamicBrowse openLocalizedCategoryBrowse() {
        sideMenu.openItem(LOCALIZED_DYNAMIC)
    }

    DiscountBrowser openDiscounts() {
        sideMenu.openItem(DISCOUNTS)
    }

    WDEnabledEntityBrowse openWDEnabledEntityBrowse(){
        sideMenu.openItem(WD_ENABLED_ENTITIES_BROWSE)
    }

    WDDisabledEntityBrowse openWDDisabledEntityBrowse(){
        sideMenu.openItem(WD_DISABLED_ENTITIES_BROWSE)
    }

    WebDAVDocumentBrowse openWebDAVDocumentBrowse() {
        sideMenu.openItem(WEBDAV_DOCUMENT_BROWSE)
    }

    EmailTemplateBrowse openEmailTemplateBrowse() {
        sideMenu.openItem(EMAIL_TEMPLATE_BROWSE)
    }
}
