package io.jmix.tests.sampler.button

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssClass
import static com.codeborne.selenide.Condition.text
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class PopupButtonSimpleUiTest extends BaseSamplerUiTest {

    public static final String MESSAGE = 'Saved as DOC'
    public static final String SAVE_AS_DOC = 'Save as DOC'

    @Test
    @DisplayName("Checks popup button")
    void checkPopupButton() {
        openSample('popupbutton-simple')
        $j(Button.class, 'popupButton1')
                .shouldBe(VISIBLE)
                .shouldHave(caption('Save'))
                .click()
        $j(Button.class, 'popupAction1')
                .shouldBe(VISIBLE)
                .shouldHave(text(SAVE_AS_DOC))
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption(MESSAGE))
    }

    @Test
    @DisplayName("Checks popup button with caption and icon")
    void checkPopupButtonWithCaptionAndIcon() {
        openSample('popupbutton-simple')
        $j(Button.class, 'popupButton2')
                .shouldBe(VISIBLE)
                .shouldHave(cssClass('icon'))
                .shouldHave(caption('Save'))
                .click()
        $j(Button.class, 'popupAction1')
                .shouldBe(VISIBLE)
                .shouldHave(text(SAVE_AS_DOC))
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption(MESSAGE))
    }

    @Test
    @DisplayName("Checks popup button with icon")
    void checkPopupButtonWithIcon() {
        openSample('popupbutton-simple')
        $j(Button.class, 'popupButton3')
                .shouldBe(VISIBLE)
                .shouldHave(cssClass('v-button-icon'))
                .click()
        $j(Button.class, 'saveAsDocAction')
                .shouldBe(VISIBLE)
                .shouldHave(text(SAVE_AS_DOC))
                .click()
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption(MESSAGE))
    }
}
