package io.jmix.tests.sampler.checkbox

import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class CheckboxSimpleUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks checkBox")
    void checkCheckBox() {
        openSample('checkbox-simple')
        CheckBox checkBox = $j(CheckBox.class, 'carField')
                .shouldBe(VISIBLE)
        checkBox.setChecked(false)
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("I don't have a car"))
        checkBox.setChecked(true)
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption('I have a car'))
    }
}
