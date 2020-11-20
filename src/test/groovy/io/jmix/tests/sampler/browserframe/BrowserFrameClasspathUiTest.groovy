package io.jmix.tests.sampler.browserframe

import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selectors.byTagName
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.switchTo
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId

@ExtendWith(ChromeExtension)
class BrowserFrameClasspathUiTest extends BaseSamplerUiTest {

    @ParameterizedTest(name = "Checks BrowserFrame with ClasspathResource")
    @ValueSource(strings = ["programmaticBrowserFrame", "declarativeBrowserFrame"])
    void checkBrowserFrameClasspathResource(String browserFrameJTestId) {
        openSample('browserframe-classpath')
        def iframe = $(byChain(byJTestId(browserFrameJTestId), byTagName('iframe')))
                .shouldBe(visible)
        switchTo().frame(iframe.attr("name"))
        $(byClassName('browser-frame-header'))
                .shouldBe(visible)
                .shouldHave(text('Classpath'))
    }
}
