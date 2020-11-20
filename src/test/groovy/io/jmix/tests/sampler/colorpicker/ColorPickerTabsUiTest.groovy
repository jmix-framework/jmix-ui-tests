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
class ColorPickerTabsUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks ColorPicker with tabs")
    void checkColorPickerTabs() {
        openSample('colorpicker-tabs')
        $j(Button.class, 'colorPicker')
                .shouldBe(VISIBLE)
                .click()
        $(byChain(byClassName('v-tabsheet-tabitemcell'), byText("HSV")))
                .shouldBe(VISIBLE)
                .click()
        $(byChain(byClassName('v-slot-hue-slider'), byText("Hue")))
                .shouldBe(VISIBLE)
        $(byChain(byClassName('v-tabsheet-tabitemcell'), byText("Swatches")))
                .shouldBe(VISIBLE)
                .click()
        $(byClassName('v-filterselect-no-input'))
                .shouldBe(VISIBLE)
        $(byChain(byClassName('v-tabsheet-tabitemcell'), byText("RGB")))
                .shouldBe(VISIBLE)
                .click()
        $(byClassName('v-textfield-v-colorpicker-preview-textfield'))
                .shouldBe(VISIBLE)
                .setValue('#fff864')
        $(byChain(byClassName('v-button'), byText("OK")))
                .shouldBe(VISIBLE)
                .click()
    }
}
