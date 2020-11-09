package io.jmix.tests.sampler.combobox

import com.codeborne.selenide.SelenideElement
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssValue
import static com.codeborne.selenide.Selectors.byClassName
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class ComboBoxOptionsStyleProviderUiTest extends BaseSamplerUiTest {

    public static final String BLUE_COLOR = 'rgba(0, 0, 255, 1)'
    public static final String GREEN_COLOR = 'rgba(0, 128, 0, 1)'
    public static final String RED_COLOR = 'rgba(255, 0, 0, 1)'
    public static final String PREMIUM = 'v-filterselect-item-premium-grade'
    public static final String HIGH = 'v-filterselect-item-high-grade'
    public static final String STANDARD = 'v-filterselect-item-standard-grade'

    @Test
    @DisplayName("Checks OptionsStyleProvider for ComboBox items")
    void checkComboBoxOptionsStyleProvider() {
        openSample('combobox-options-style-provider')
        SelenideElement comboBox = $j(ComboBox.class, 'comboBox')
                .shouldBe(VISIBLE)
                .openOptionsPopup()
                .delegate
        checkBackgroundColor(comboBox, PREMIUM, RED_COLOR)
        checkBackgroundColor(comboBox, HIGH, GREEN_COLOR)
        checkBackgroundColor(comboBox, STANDARD, BLUE_COLOR)
    }

    private void checkBackgroundColor(SelenideElement comboBoxItem, String classValue, String colorValue) {
        comboBoxItem
                .$(byClassName(classValue))
                .shouldHave(cssValue('background-color', colorValue))
    }
}
