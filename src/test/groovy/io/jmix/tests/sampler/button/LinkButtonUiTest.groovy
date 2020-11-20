package io.jmix.tests.sampler.button

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class LinkButtonUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks link button")
    void checkLinkButton() {
        openSample('linkbutton')
        $j(Button.class, 'helloButton')
                .shouldBe(VISIBLE)
                .shouldHave(cssClass('v-button-link'))
                .shouldHave(caption("Say Hello!"))
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption('Hello, World!'))
    }

    @Test
    @DisplayName("Checks link button with caption and icon")
    void checkLinkButtonCaptionWithIcon() {
        openSample('linkbutton')
        $j(Button.class, 'saveButton1')
                .shouldBe(VISIBLE)
                .shouldHave(cssClass('v-button-link'))
                .shouldHave(cssClass('icon'))
                .shouldHave(caption("Save"))
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption('Save called from saveButton1'))
    }

    @Test
    @DisplayName("Checks link button with icon")
    void checkLinkButtonWithIcon() {
        openSample('linkbutton')
        $j(Button.class, 'saveButton2')
                .shouldBe(VISIBLE)
                .shouldHave(cssClass('v-button-link'))
                .shouldHave(cssClass('icon'))
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption('Save called from saveButton2'))
    }
}
