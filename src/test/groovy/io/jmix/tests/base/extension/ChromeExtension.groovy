package io.jmix.tests.base.extension


import org.openqa.selenium.Capabilities
import org.openqa.selenium.chrome.ChromeOptions

class ChromeExtension extends BrowserExtension {

    @Override
    Capabilities getCapabilities() {
        return new ChromeOptions()
    }
}
