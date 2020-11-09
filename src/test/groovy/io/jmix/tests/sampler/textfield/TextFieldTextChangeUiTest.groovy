package io.jmix.tests.sampler.textfield


import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.TAB

@ExtendWith(ChromeExtension)
class TextFieldTextChangeUiTest extends BaseSamplerUiTest {
    @Test
    @DisplayName("Check TextChangeListener for TextField")
    void checkTextChangeListener() {
        openSample('textfield-text-change')
        $j(TextField.class, 'textField')
                .setValue("Some value")
                .delegate
                .sendKeys(TAB)
        $('.v-Notification-humanized')
                .shouldBe(VISIBLE)
                .shouldHave(textCaseSensitive('Text Changed: Some value'))
    }
}
