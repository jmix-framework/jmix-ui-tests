package io.jmix.tests.sampler.browserframe

import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selectors.byTagName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId

@ExtendWith(ChromeExtension)
class BrowserFrameUrlUiTest extends BaseSamplerUiTest {

    @ParameterizedTest(name = "Checks source with UrlResource for BrowserFrame")
    @ValueSource(strings = ["programmaticBrowserFrame", "declarativeBrowserFrame"])
    void checkBrowserFrameUrlResource(String browserFrameJTestId) {
        openSample('browserframe-url')
        $(byChain(byJTestId(browserFrameJTestId), byTagName('iframe')))
                .shouldBe(visible)
    }
}
