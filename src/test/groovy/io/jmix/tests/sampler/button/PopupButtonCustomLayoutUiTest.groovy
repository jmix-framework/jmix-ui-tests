package io.jmix.tests.sampler.button

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class PopupButtonCustomLayoutUiTest extends BaseSamplerUiTest {

    public static final String SENT = 'Sent'
    public static final String TEST = 'test'

    @Test
    @DisplayName("Checks popup button with custom layout")
    void checkPopupButtonCustomLayout() {
        openSample('popupbutton-custom-layout')
        openSettingsPopup()
        TextField textField = $j(TextField.class, 'textField')
                .shouldBe(VISIBLE)
        textField.setValue(TEST)
        ComboBox comboBox = $j(ComboBox.class, 'comboBox')
                .shouldBe(VISIBLE)
        comboBox
                .openOptionsPopup()
                .select(SENT)
        $j(Button.class, 'saveAndCloseButton')
                .shouldBe(VISIBLE)
                .click()
        $j(Notification.class)
                .shouldHave(caption('Settings Saved'))
        openSettingsPopup()
        textField.shouldHave(value(TEST))
        comboBox.shouldHave(value(SENT))
        $j(Button.class, 'cancelAndCloseButton')
                .shouldBe(VISIBLE)
                .click()
        $j(Notification.class)
                .shouldHave(caption('Cancelled'))
    }

    private Button openSettingsPopup() {
        $j(Button.class, 'popupButton')
                .shouldBe(VISIBLE)
                .shouldHave(caption('Settings'))
                .click()
    }
}
