package io.jmix.tests.sampler.checkbox

import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.Label
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import java.util.stream.Stream

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class CheckboxDataawareUiTest extends BaseSamplerUiTest {

    protected static Stream<Arguments> provideTestArguments() {
        return Stream.of(
                Arguments.of(true, 'True'),
                Arguments.of(false, 'False')
        )
    }

    @ParameterizedTest(name = "Checks set {0} value to data-aware checkBox")
    @MethodSource('provideTestArguments')
    void checkCheckBoxDataAware(boolean checkedCheckbox, String labelValue) {
        openSample('checkbox-dataaware')
        $j(CheckBox.class, 'active')
                .shouldBe(VISIBLE)
                .setChecked(checkedCheckbox)
        $j(Label.class, 'labelValue')
                .shouldBe(VISIBLE)
                .shouldHave(value(labelValue))
    }
}
