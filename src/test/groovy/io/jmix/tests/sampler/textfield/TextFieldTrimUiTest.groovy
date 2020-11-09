package io.jmix.tests.sampler.textfield

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.Label
import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class TextFieldTrimUiTest extends BaseSamplerUiTest {

    @DisplayName("Check trim option for TextField")
    @ParameterizedTest(name = "Trim = {0}; Value to set = {1}; Expected value = {2}")
    @CsvSource(value = [
            "true, 'Value   ', Value: 'Value'",
            "false, 'Value   ', Value: 'Value   '",
            "true, '   ', Value: 'null'",
            "false, '   ', Value: '   '"
    ])
    void checkTextFieldTrimOption(boolean isTrim, String valueToSet, String expectedValue) {
        openSample('textfield-trim')
        $j(CheckBox.class, 'trim')
                .shouldBe(EDITABLE)
                .setChecked(isTrim)
        $j(TextField.class, 'textField')
                .shouldBe(EDITABLE)
                .setValue(valueToSet)
        $j(Button.class, 'show')
                .click()
        $j(Label.class, 'valueLabel')
                .shouldHave(value(expectedValue))
    }
}
