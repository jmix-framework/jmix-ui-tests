package io.jmix.tests.sampler.colorpicker

import io.jmix.masquerade.component.Button
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
class ColorPickerDefaultCaptionUiTest extends BaseSamplerUiTest {

    public static final String COLOR_VALUE = "#abfabf"

    @Test
    @DisplayName("Checks ColorPicker with default caption")
    void checkColorPickerDefaultCaption() {
        openSample('colorpicker-default-caption')
        Button button = $j(Button.class, 'colorPicker')
                .shouldBe(VISIBLE)
        button
                .shouldHave(caption("#ffffff"))
                .click()
        $(byClassName('v-textfield-v-colorpicker-preview-textfield'))
                .shouldBe(VISIBLE)
                .setValue(COLOR_VALUE)
        $(byChain(byClassName('v-button'), byText("OK")))
                .shouldBe(VISIBLE)
                .click()
        button.shouldHave(caption(COLOR_VALUE))
    }
}
