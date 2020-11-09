package io.jmix.tests.sampler.combobox

import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Selectors.byClassName
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class ComboBoxUserInputUiTest extends BaseSamplerUiTest {

    public static final String FIVE = 'five'

    @Test
    @DisplayName("Checks if user input is saved in ComboBox options")
    void checkComboBoxUserInput() {
        openSample('combobox-user-input')
        ComboBox comboBox = $j(ComboBox.class, 'comboBox')
                .shouldBe(VISIBLE)
        comboBox.setFilter(FIVE)
                .delegate
                .$(byClassName('v-filterselect-input'))
                .sendKeys(ENTER)
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("five added"))
                .clickToClose()
        comboBox
                .openOptionsPopup()
                .select(FIVE)
                .shouldHave(value(FIVE))
    }
}
