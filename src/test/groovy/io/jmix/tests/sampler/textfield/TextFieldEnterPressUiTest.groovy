package io.jmix.tests.sampler.textfield

import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class TextFieldEnterPressUiTest extends BaseSamplerUiTest {
    @Test
    @DisplayName("Check EnterPressListener for TextField")
    void checkEnterPressListener() {
        openSample('textfield-enter-press')
        $j(TextField.class, 'textField')
                .setValue("Some value")
                .delegate
                .sendKeys(ENTER)
        $('.v-Notification-humanized')
                .shouldHave(textCaseSensitive('Enter Pressed: Some value'))
    }
}
