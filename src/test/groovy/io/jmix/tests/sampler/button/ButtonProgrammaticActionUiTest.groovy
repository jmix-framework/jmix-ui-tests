package io.jmix.tests.sampler.button

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class ButtonProgrammaticActionUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks button programmatic action")
    void checkButtonProgrammaticAction() {
        openSample('button-programmatic-action')
        $j(Button.class, 'buttonAction')
                .shouldHave(caption("Click me!"))
                .click()
        $j(Notification.class)
                .shouldHave(caption('Action performed'))
    }
}
