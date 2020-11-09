package io.jmix.tests.sampler.textfield

import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class TextFieldSimpleUiTest extends BaseSamplerUiTest {

    public static final String LONG_VALUE = "123456789012"
    public static final String CUT_VALUE = "1234567890"

    @Test
    @DisplayName("Check that user can input any length value in field without maxLength attribute")
    void checkTextFieldWithoutMaxLength() {
        openSample('textfield-simple')
        $j(TextField.class, 'simpleCaptionTextField')
                .setValue(LONG_VALUE)
                .shouldHave(value(LONG_VALUE))
    }

    @Test
    @DisplayName("Check that input value is cut if it`s length is more than maxLength value")
    void checkTextFieldMaxLength() {
        openSample('textfield-simple')
        $j(TextField.class, 'captionLengthTextField')
                .setValue(LONG_VALUE)
                .shouldHave(value(CUT_VALUE))
    }
}
