package io.jmix.tests.extension

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class LocallyRunExtension implements AfterEachCallback {

    /**
     * Closes web driver after each test
     */
    @Override
    void afterEach(ExtensionContext context) throws Exception {
        WebDriverRunner.webDriver.manage().deleteAllCookies()
        Selenide.closeWebDriver()
    }
}
