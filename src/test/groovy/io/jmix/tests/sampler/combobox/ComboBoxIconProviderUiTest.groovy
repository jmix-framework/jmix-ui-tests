package io.jmix.tests.sampler.combobox

import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Selectors.byClassName
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class ComboBoxIconProviderUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Checks setNewOptionHandler method for ComboBox")
    void checkComboBoxSetNewOptionHandler() {
        openSample('combobox-icon-provider')
        $j(ComboBox.class, 'comboBox')
                .shouldBe(VISIBLE)
                .openOptionsPopup()
                .select('TXT file')
                .delegate
                .$(byClassName('v-icon'))
                .shouldBe(VISIBLE)
    }
}
