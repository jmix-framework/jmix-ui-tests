package io.jmix.tests.sampler.colorpicker

import io.jmix.masquerade.component.Button
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class ColorPickerCaptionsUiTest extends BaseSamplerUiTest {
    @Test
    @DisplayName("Checks ColorPicker with caption")
    void checkColorPickerCaption() {
        openSample('colorpicker-captions')
        $j(Button.class, 'colorPicker')
                .shouldBe(VISIBLE)
                .click()
        $(byText("Choose a color"))
                .shouldBe(VISIBLE)
        $(byChain(byClassName('v-tabsheet-tabitemcell'), byText("Samples")))
                .shouldBe(VISIBLE)
                .click()
        $(byClassName('v-textfield-v-colorpicker-preview-textfield'))
                .shouldBe(VISIBLE)
                .setValue('#fff864')
        $(byChain(byClassName('v-button'), byText("Close")))
                .shouldBe(VISIBLE)
        $(byChain(byClassName('v-button'), byText("Apply")))
                .shouldBe(VISIBLE)
                .click()
    }
}
