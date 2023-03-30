package io.jmix.tests.extension

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.extension.*
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import org.rnorth.ducttape.timeouts.Timeouts
import org.rnorth.ducttape.unreliables.Unreliables
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.BrowserWebDriverContainer

import java.util.concurrent.TimeUnit

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
                .withCapabilities(capabilities)
        browser.start()
        RemoteWebDriver remoteWebDriver = getWebDriver(35, 25, browser.seleniumAddress)
        remoteWebDriver.setFileDetector(new LocalFileDetector())
        WebDriverRunner.setWebDriver(remoteWebDriver)

        printVncRecordedUrl()
    }

    // Workaround, see https://github.com/jmix-framework/jmix-ui-tests/issues/48
    // CAUTION! Copied form BrowserWebDriverContainer#getWebDriver()
    RemoteWebDriver getWebDriver(int retryTimeOut, int performTimeOut, URL seleniumAddress) {
        Unreliables.retryUntilSuccess(retryTimeOut, TimeUnit.SECONDS, () -> {
            return Timeouts.getWithTimeout(performTimeOut, TimeUnit.SECONDS, () -> {
                return new RemoteWebDriver(seleniumAddress, capabilities);
            });
        });
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
