package io.jmix.tests.sampler.checkboxgroup

import io.jmix.masquerade.component.OptionsGroup
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class CheckBoxGroupDataawareUiTest extends BaseSamplerUiTest {

    public static final String DVD = 'DVD'

    @Test
    @DisplayName("Checks CheckBoxGroup with optionsContainer")
    void checkCheckBoxGroupOptionsContainer() {
        openSample('checkboxgroup-dataaware')
        def optionsGroup = $j(OptionsGroup.class, 'checkBoxGroup')
                .shouldBe(VISIBLE)
        optionsGroup.select(DVD)
        optionsGroup.shouldHave(value(DVD))
    }
}
