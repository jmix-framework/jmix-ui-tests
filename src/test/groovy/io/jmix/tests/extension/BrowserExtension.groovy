package io.jmix.tests.extension

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.openqa.selenium.Capabilities
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.BrowserWebDriverContainer

abstract class BrowserExtension implements BeforeEachCallback, AfterEachCallback {

    public static final int VNC_RECORDER_PORT = 5900

    private static final Logger log = LoggerFactory.getLogger(BrowserExtension.class)

    private BrowserWebDriverContainer browser

    @Override
    void beforeEach(ExtensionContext context) throws Exception {
        browser = new BrowserWebDriverContainer()
                .withCapabilities(getCapabilities())
        browser.start()
        WebDriverRunner.setWebDriver(browser.getWebDriver())

        printVncRecordedUrl()
    }

    @Override
    void afterEach(ExtensionContext context) throws Exception {
        WebDriverRunner.webDriver.manage().deleteAllCookies()
        Selenide.closeWebDriver()
        browser.stop()
    }

    abstract Capabilities getCapabilities()

    void printVncRecordedUrl() {
        log.info('VNC recorder url: vnc://' +
                browser.getContainerIpAddress() +
                ':' +
                browser.getMappedPort(VNC_RECORDER_PORT) +
                ', password: \'secret\'')
    }
}
