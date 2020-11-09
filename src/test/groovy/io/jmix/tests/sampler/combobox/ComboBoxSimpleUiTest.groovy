package io.jmix.tests.sampler.combobox

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

@ExtendWith(ChromeExtension)
class ComboBoxSimpleUiTest extends BaseSamplerUiTest {

    public static final String STANDARD = "Standard"

    @Test
    @DisplayName("Checks selection enum value in ComboBox")
    void checkComboBoxValueSelect() {
        openSample('combobox-simple')
        $j(ComboBox.class, 'grade')
                .shouldBe(Conditions.VISIBLE)
                .openOptionsPopup()
                .select(byText(STANDARD))
                .shouldHave(Conditions.value(STANDARD))
    }
}
