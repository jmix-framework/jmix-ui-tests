package io.jmix.tests.sampler.textfield

import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.MaskedField
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class MaskedFieldSimpleUiTest extends BaseSamplerUiTest {

    public static final String INPUT_VALUE = '1111111111'
    public static final String MASKED_VALUE = '(111) 111-11-11'

    @Test
    @DisplayName("Check masked valueMode for textField")
    void checkTextFieldWithMaskedValueMode() {
        openSample('maskedfield-simple')
        $j(MaskedField.class, 'phoneFieldMasked')
                .setValue(INPUT_VALUE)
        $j(Button.class, 'showMasked')
                .click()
        $j(Notification.class)
                .shouldHave(caption(MASKED_VALUE))
    }

    @Test
    @DisplayName("Check clear valueMode for textField")
    void checkTextFieldWithClearValueMode() {
        openSample('maskedfield-simple')
        $j(MaskedField.class, 'phoneFieldClear')
                .setValue(INPUT_VALUE)
        $j(Button.class, 'showClear')
                .click()
        $j(Notification.class)
                .shouldHave(caption(INPUT_VALUE))
    }
}
