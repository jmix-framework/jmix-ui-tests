package io.jmix.tests.sampler.button

import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.interactions.Actions

import static com.codeborne.selenide.Condition.exactText
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.WebDriverRunner.webDriver
import static io.jmix.masquerade.Selectors.byJTestId

@ExtendWith(ChromeExtension)
class ButtonLocalizedUiTest extends BaseSamplerUiTest {

    public static final String TOOLTIP_TEXT = 'An example of a button with a tooltip and \
                        a caption retrieved from a localized message pack'

    @Test
    @DisplayName("Checks localized button description")
    void checkLocalizedButtonDescription() {
        openSample('button-localized')
        new Actions(getWebDriver())
                .moveToElement($(byJTestId('localizedButton')))
                .perform()
        $(byClassName('v-tooltip-text'))
                .shouldHave(exactText(TOOLTIP_TEXT))
    }
}
