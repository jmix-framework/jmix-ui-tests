package io.jmix.tests.extension


import org.openqa.selenium.Capabilities
import org.openqa.selenium.firefox.FirefoxOptions

class FirefoxExtension extends BrowserExtension {

    @Override
    Capabilities getCapabilities() {
        return new FirefoxOptions()
    }
}
