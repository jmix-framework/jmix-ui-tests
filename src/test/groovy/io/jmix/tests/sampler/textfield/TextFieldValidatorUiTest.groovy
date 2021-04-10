package io.jmix.tests.sampler.textfield


import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class TextFieldValidatorUiTest extends BaseSamplerUiTest {

    public static final String NEGATIVE_VALUE = '-123'
    public static final String INTEGER_VALUE = '123'
    public static final String VALIDATION_SUCCESSFUL = 'Validation successful'

    @Test
    @DisplayName("Checks negative integer value validation")
    void checkTextFieldNegativeValidator() {
        openSample('textfield-validator')
        def integerTextField = $j(TextField.class, 'integerTextField')
        def validateButton = $j(Button.class, 'validate')
        integerTextField
                .setValue(NEGATIVE_VALUE)
        validateButton.click()
        $('.v-Notification-error')
                .shouldHave(textCaseSensitive('Validation failed: Value must be positive'))
                .click()
        integerTextField
                .setValue(INTEGER_VALUE)
        validateButton.click()
        $j(Notification.class)
                .shouldHave(caption(VALIDATION_SUCCESSFUL))
    }

    @Test
    @DisplayName("Checks border value validation")
    void checkTextFieldBorderValidator() {
        openSample('textfield-validator')
        def doubleTextField = $j(TextField.class, 'doubleTextField')
        def validateButton = $j(Button.class, 'validate')
        doubleTextField
                .setValue('0.1')
        validateButton.click()
        $('.v-Notification-error')
                .shouldHave(textCaseSensitive("Validation failed: " +
                        "Value '0.1' should be greater than or equal to '1'"))
                .click()
        doubleTextField
                .setValue('100.1')
        validateButton.click()
        $('.v-Notification-error')
                .shouldHave(textCaseSensitive("Validation failed: " +
                        "Value '100.1' should be less than or equal to '100'"))
                .click()
        doubleTextField
                .setValue('99.9')
        validateButton.click()
        $j(Notification.class)
                .shouldHave(caption(VALIDATION_SUCCESSFUL))
    }
}
