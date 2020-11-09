package io.jmix.tests.sampler.textfield

import io.jmix.masquerade.component.OptionsGroup
import io.jmix.masquerade.component.TextField
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.PAGE_DOWN
import static org.openqa.selenium.Keys.TAB

@ExtendWith(ChromeExtension)
class TextFieldTextChangeEventModeUiTest extends BaseSamplerUiTest {

    public static final String BLUR = "BLUR"
    public static final String EAGER = "EAGER"
    public static final String LAZY = "LAZY"
    public static final String TIMEOUT = "TIMEOUT"
    public static final int DEFAULT_TIMEOUT = 400

    @Test
    @DisplayName("Check Blur ChangeEventMode for TextField")
    void checkTextFieldBlurChangeEventMode() {
        openSample('textfield-text-change-event-mode')
        $j(OptionsGroup.class, 'modeGroup')
                .select(BLUR)
        def textField = $j(TextField.class, 'textField')
        textField
                .setValue('12345')
                .delegate
                .sendKeys(TAB)
        // .shouldHave(caption("Characters left: 135"))
        //TODO https://github.com/Haulmont/jmix-masquerade/issues/1
    }

    @Test
    @DisplayName("Check Eager ChangeEventMode for TextField")
    void checkTextFieldEagerChangeEventMode() {
        openSample('textfield-text-change-event-mode')
        $j(OptionsGroup.class, 'modeGroup')
                .select(EAGER)
        def textField = $j(TextField.class, 'textField')
        textField.setValue('123')
        // .shouldHave(caption("Characters left: 137"))
        //TODO https://github.com/Haulmont/jmix-masquerade/issues/1
    }

    @Test
    @DisplayName("Check Lazy ChangeEventMode for TextField")
    void checkTextFieldLazyChangeEventMode() {
        openSample('textfield-text-change-event-mode')
        $j(OptionsGroup.class, 'modeGroup')
                .select(LAZY)
        def textField = $j(TextField.class, 'textField')
        textField.setValue('123')
        sleep(DEFAULT_TIMEOUT)
        textField
                .delegate
                .sendKeys(PAGE_DOWN, "456")
        //.shouldHave(caption("Characters left: 123456"))
        //TODO https://github.com/Haulmont/jmix-masquerade/issues/1
    }

    @Test
    @DisplayName("Check Timeout ChangeEventMode for TextField")
    void checkTextFieldTimeoutChangeEventMode() {
        openSample('textfield-text-change-event-mode')
        $j(OptionsGroup.class, 'modeGroup')
                .select(TIMEOUT)
        def textField = $j(TextField.class, 'textField')
        textField.setValue('123')
        //.shouldNotHave(caption("Characters left: 123"))
        sleep(DEFAULT_TIMEOUT)
        // textField.shouldHave(caption("Characters left: 123"))
        //TODO https://github.com/Haulmont/jmix-masquerade/issues/1
    }
}
