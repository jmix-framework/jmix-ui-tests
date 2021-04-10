package io.jmix.tests.sampler.button

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class ButtonActionUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks button with action")
    void checkButtonAction() {
        openSample('button-action')
        $j(Button.class, 'someAction')
                .shouldBe(VISIBLE)
                .shouldHave(caption("Click me!"))
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption('Action performed'))
    }
}
