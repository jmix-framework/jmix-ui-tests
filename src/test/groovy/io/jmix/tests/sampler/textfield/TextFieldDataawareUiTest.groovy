package io.jmix.tests.sampler.textfield

import io.jmix.masquerade.component.Label
import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class TextFieldDataawareUiTest extends BaseSamplerUiTest {

    public static final String DEFAULT_VALUE = 'John'
    public static final String CHANGED_VALUE = 'Smith'

    @Test
    @DisplayName("Check that predefined value in field is set, change value")
    void checkDataawareTextField() {
        openSample('textfield-dataaware')
        $j(TextField.class, 'name')
                .shouldHave(value(DEFAULT_VALUE))
                .setValue(CHANGED_VALUE)
                .delegate
                .sendKeys(ENTER)
        $j(Label.class, 'labelName')
                .shouldHave(value(CHANGED_VALUE))
    }
}
