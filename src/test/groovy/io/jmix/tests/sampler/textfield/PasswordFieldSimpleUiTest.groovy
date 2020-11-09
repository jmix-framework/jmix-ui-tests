package io.jmix.tests.sampler.textfield


import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.PasswordField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.ENTER
import static org.openqa.selenium.Keys.TAB

@ExtendWith(ChromeExtension)
class PasswordFieldSimpleUiTest extends BaseSamplerUiTest {

    public static final String INPUT_VALUE = '1234567890'

    @Test
    @DisplayName("Check password field")
    void checkPasswordField() {
        openSample('passwordfield-simple')
        $j(PasswordField.class, 'passwordField')
                .setValue(INPUT_VALUE)
                .delegate
                .sendKeys(TAB, ENTER)
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption(INPUT_VALUE))
    }
}
