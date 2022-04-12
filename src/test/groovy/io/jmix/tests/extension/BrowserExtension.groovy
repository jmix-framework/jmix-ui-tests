package io.jmix.tests.extension

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.extension.*
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.BrowserWebDriverContainer

abstract class BrowserExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    public static final int VNC_RECORDER_PORT = 5900

    private static final Logger log = LoggerFactory.getLogger(BrowserExtension.class)

    private BrowserWebDriverContainer browser

    @Override
    void beforeAll(ExtensionContext context) throws Exception {
        startBrowser()
    }

    @Override
    void beforeEach(ExtensionContext context) throws Exception {
        refreshBrowser()
    }

    @Override
    void afterEach(ExtensionContext context) throws Exception {
//        refreshBrowser()
    }

    @Override
    void afterAll(ExtensionContext context) throws Exception {
        stopBrowser()
    }

    void startBrowser() {
        browser = new BrowserWebDriverContainer()
                .withCapabilities(getCapabilities())
        browser.start()
        RemoteWebDriver remoteWebDriver = browser.getWebDriver()
        remoteWebDriver.setFileDetector(new LocalFileDetector())
        WebDriverRunner.setWebDriver(remoteWebDriver)

        printVncRecordedUrl()
    }

    void stopBrowser() {
        WebDriverRunner.webDriver.manage().deleteAllCookies()
        Selenide.closeWebDriver()
        browser.stop()
        browser = null
    }

    void refreshBrowser() {
        if (browser != null) {
            stopBrowser()
        }

        startBrowser()
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
