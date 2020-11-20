package io.jmix.tests.sampler.colorpicker

import com.codeborne.selenide.Condition
import io.jmix.masquerade.component.Button
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class ColorPickerDataawareUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks ColorPicker with default value")
    void checkColorPickerDefaultValue() {
        openSample('colorpicker-dataaware')
        $j(Button.class, 'hex')
                .shouldBe(VISIBLE)
                .click()
        $(byClassName('v-textfield-v-colorpicker-preview-textfield'))
                .shouldBe(VISIBLE)
                .setValue("#abfabf")
        $(byChain(byClassName('v-button'), byText("OK")))
                .shouldBe(VISIBLE)
                .click()
        $(byJTestId('labelValue'))
                .shouldBe(VISIBLE)
                .shouldHave(text('abfabf'))
    }
}
