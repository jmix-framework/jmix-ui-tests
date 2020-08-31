package io.jmix.tests.base.extension

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.Capabilities
import org.testcontainers.containers.BrowserWebDriverContainer

abstract class BrowserExtension implements BeforeEachCallback, AfterEachCallback {

    private BrowserWebDriverContainer browser

    @Override
    void beforeEach(ExtensionContext context) throws Exception {
        browser = new BrowserWebDriverContainer().withCapabilities(getCapabilities())
        browser.start()
        WebDriverRunner.setWebDriver(browser.getWebDriver())
        Selenide.open('/')
    }

    @Override
    void afterEach(ExtensionContext context) throws Exception {
        WebDriverRunner.webDriver.manage().deleteAllCookies()
        Selenide.closeWebDriver()
        browser.stop()
    }

    abstract Capabilities getCapabilities()
}
