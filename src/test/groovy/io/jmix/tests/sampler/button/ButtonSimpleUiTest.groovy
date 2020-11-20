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
import static io.jmix.masquerade.Selectors.byClassName

@ExtendWith(ChromeExtension)
class ButtonSimpleUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks that user can click on simple button")
    void checkSimpleButton() {
        openSample('button-simple')

        $j(Button.class, 'helloButton')
                .shouldHave(caption('Say Hello!'))
                .click()

        $j(Notification.class)
                .shouldHave(caption('Hello, world!'))
    }

    @Test
    @DisplayName("Checks that user can click on button with icon and caption")
    void checkSimpleButtonWithIconAndCaption() {
        openSample('button-simple')
        $j(Button.class, 'saveButton1')
                .shouldBe(VISIBLE)
                .delegate
                .$(byClassName('v-icon'))
                .shouldBe(VISIBLE)
        $j(Button.class, 'saveButton1')
                .shouldHave(caption('Save'))
                .click()
        $j(Notification.class)
                .shouldHave(caption('Save called from saveButton1'))
    }

    @Test
    @DisplayName("Checks that user can click on button with icon")
    void checkSimpleButtonWithIcon() {
        openSample('button-simple')
        $j(Button.class, 'saveButton2')
                .shouldBe(VISIBLE)
                .delegate
                .$(byClassName('v-icon'))
                .shouldBe(VISIBLE)
        $j(Button.class, 'saveButton2')
                .click()
        $j(Notification.class)
                .shouldHave(caption('Save called from saveButton2'))
    }
}
