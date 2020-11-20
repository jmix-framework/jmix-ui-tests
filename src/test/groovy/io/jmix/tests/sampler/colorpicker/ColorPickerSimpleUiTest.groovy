package io.jmix.tests.sampler.colorpicker

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class ColorPickerSimpleUiTest extends BaseSamplerUiTest {

    public static final String COLOR_VALUE = "#abfabf"

    @Test
    @DisplayName("Checks simple ColorPicker")
    void checkColorPickerSimple() {
        openSample('colorpicker-simple')
        $j(Button.class, 'colorPicker')
                .shouldBe(VISIBLE)
                .click()
        $(byClassName('v-textfield-v-colorpicker-preview-textfield'))
                .shouldBe(VISIBLE)
                .setValue(COLOR_VALUE)
        $(byChain(byClassName('v-button'), byText("OK")))
                .shouldBe(VISIBLE)
                .click()
        $(byChain(byClassName('v-button'), byText("Show value")))
                .shouldBe(VISIBLE)
                .click()
        $j(Notification.class)
                .shouldHave(caption("abfabf"))
    }
}
