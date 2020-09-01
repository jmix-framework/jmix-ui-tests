package io.jmix.tests.sampler

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class ButtonSamplerUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks that user can click on simple button")
    void clickOnSimpleButton() {
        openSample('button-simple')

        $j(Button.class, 'helloButton')
                .shouldHave(caption('Say Hello!'))
                .click()

        $j(Notification.class)
                .shouldHave(caption('Hello, world!'))
    }
}
