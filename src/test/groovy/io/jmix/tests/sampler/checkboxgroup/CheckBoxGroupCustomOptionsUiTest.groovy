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
class CheckBoxGroupCustomOptionsUiTest extends BaseSamplerUiTest {

    public static final String SEVEN = '7'
    public static final String FIVE = 'five'
    public static final String PREMIUM = 'Premium'

    @Test
    @DisplayName("Checks CheckBoxGroup with setOptionsList()")
    void checkCheckBoxGroupOptionsList() {
        openSample('checkboxgroup-custom-options')
        def optionsGroup = $j(OptionsGroup.class, 'checkBoxGroupWithList')
                .shouldBe(VISIBLE)
        optionsGroup.select(SEVEN)
        optionsGroup.shouldHave(value(SEVEN))
    }

    @Test
    @DisplayName("Checks CheckBoxGroup with setOptionsMap()")
    void checkCheckBoxGroupOptionsMap() {
        openSample('checkboxgroup-custom-options')
        def optionsGroup = $j(OptionsGroup.class, 'checkBoxGroupWithMap')
                .shouldBe(VISIBLE)
        optionsGroup.select(FIVE)
        optionsGroup.shouldHave(value(FIVE))
    }

    @Test
    @DisplayName("Checks CheckBoxGroup with options enum")
    void checkCheckBoxGroupOptionsEnum() {
        openSample('checkboxgroup-custom-options')
        def optionsGroup = $j(OptionsGroup.class, 'checkBoxGroupWithEnum')
                .shouldBe(VISIBLE)
        optionsGroup.select(PREMIUM)
        optionsGroup.shouldHave(value(PREMIUM))
    }
}
