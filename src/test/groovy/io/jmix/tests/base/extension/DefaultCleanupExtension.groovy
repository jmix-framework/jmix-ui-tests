package io.jmix.tests.base.extension

import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

import static com.codeborne.selenide.Selenide.open

class DefaultCleanupExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    void afterEach(ExtensionContext context) throws Exception {
        WebDriverRunner.webDriver.manage().deleteAllCookies()
    }

    @Override
    void beforeEach(ExtensionContext context) throws Exception {
        open("/")
    }
}
