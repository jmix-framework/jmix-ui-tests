package io.jmix.tests.sampler.textfield


import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class TextFieldDatatypeUiTest extends BaseSamplerUiTest {

    public static final String STRING_VALUE = 'some string'
    public static final String INTEGER_VALUE = '135'
    public static final String DOUBLE_VALUE = '1.35'

    @Test
    @DisplayName("Check validation with string datatype")
    void checkTextFieldWithStringDatatype() {
        openSample('textfield-datatype')
        def stringTextField = $j(TextField.class, 'stringTextField')
        def errorValidationDatatype = cssClass("v-textfield-error")
        stringTextField
                .setValue(STRING_VALUE)
                .shouldHave(value(STRING_VALUE))
                .delegate
                .sendKeys(ENTER)
        stringTextField
                .shouldNotBe(errorValidationDatatype)
    }

    @Test
    @DisplayName("Check validation with integer datatype")
    void checkTextFieldWithIntegerDatatype() {
        openSample('textfield-datatype')
        def integerTextField = $j(TextField.class, 'integerTextField')
        def errorValidationDatatype = cssClass("v-textfield-error")
        integerTextField
                .setValue(STRING_VALUE)
                .delegate
                .sendKeys(ENTER)
        integerTextField
                .shouldBe(errorValidationDatatype)
                .setValue(INTEGER_VALUE)
                .delegate
                .sendKeys(ENTER)
        integerTextField
                .shouldNotBe(errorValidationDatatype)
    }

    @Test
    @DisplayName("Check validation with double datatype")
    void checkTextFieldWithDoubleDatatype() {
        openSample('textfield-datatype')
        def integerTextField = $j(TextField.class, 'integerTextField')
        def doubleTextField = $j(TextField.class, 'doubleTextField')
        def errorValidationDatatype = cssClass("v-textfield-error")
        doubleTextField
                .setValue(STRING_VALUE)
                .delegate
                .sendKeys(ENTER)
        doubleTextField
                .shouldBe(errorValidationDatatype)
                .setValue(DOUBLE_VALUE)
                .delegate
                .sendKeys(ENTER)
        integerTextField
                .shouldNotBe(errorValidationDatatype)
    }
}
