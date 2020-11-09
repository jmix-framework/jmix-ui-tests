package io.jmix.tests.sampler.combobox

import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class ComboBoxFilterModeUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks ComboBox with NO filtering mode")
    void checkComboBoxNoFiltering() {
        openSample('combobox-filter-mode')
        $j(ComboBox.class, 'noFilterComboBox')
                .shouldBe(VISIBLE)
                .setFilter("Sunday")
                .getOptionsPopup()
                .shouldHave(visibleOptionsCount(7))
    }

    @Test
    @DisplayName("Checks ComboBox with STARTS WITH filtering mode")
    void checkComboBoxStartsWithFiltering() {
        openSample('combobox-filter-mode')
        $j(ComboBox.class, 'startsWithFilterComboBox')
                .shouldBe(VISIBLE)
                .setFilter('Sund')
                .getOptionsPopup()
                .shouldHave(visibleOptions("Sunday"))
                .shouldHave(visibleOptionsCount(1))
    }

    @Test
    @DisplayName("Checks ComboBox with CONTAINS filtering mode")
    void checkComboBoxContainsFiltering() {
        openSample('combobox-filter-mode')
        $j(ComboBox.class, 'containsFilterComboBox')
                .shouldBe(VISIBLE)
                .setFilter('unday')
                .getOptionsPopup()
                .shouldHave(visibleOptions("Sunday"))
                .shouldHave(visibleOptionsCount(1))
    }
}
