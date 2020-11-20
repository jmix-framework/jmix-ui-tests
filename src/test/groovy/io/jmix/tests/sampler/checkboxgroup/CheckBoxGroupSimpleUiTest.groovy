package io.jmix.tests.sampler.checkboxgroup

import io.jmix.masquerade.component.OptionsGroup
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssClass
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class CheckBoxGroupSimpleUiTest extends BaseSamplerUiTest {

    public static final String PREMIUM = "Premium"

    @Test
    @DisplayName("Checks CheckBoxGroup with vertical layout")
    void checkCheckBoxGroupVerticalLayout() {
        openSample('checkboxgroup-simple')
        def optionsGroup = $j(OptionsGroup.class, 'verticalCheckBoxGroup')
                .shouldBe(VISIBLE)
        optionsGroup
                .shouldNotHave(cssClass('v-select-optiongroup-horizontal'))
                .select(PREMIUM)
        optionsGroup.shouldHave(value(PREMIUM))
    }

    @Test
    @DisplayName("Checks CheckBoxGroup with horizontal layout")
    void checkCheckBoxGroupHorizontalLayout() {
        openSample('checkboxgroup-simple')
        def optionsGroup = $j(OptionsGroup.class, 'horizontalCheckBoxGroup')
                .shouldBe(VISIBLE)
        optionsGroup
                .shouldHave(cssClass('v-select-optiongroup-horizontal'))
                .select(PREMIUM)
        optionsGroup.shouldHave(value(PREMIUM))
    }
}
