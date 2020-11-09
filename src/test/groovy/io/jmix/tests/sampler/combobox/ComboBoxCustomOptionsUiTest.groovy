package io.jmix.tests.sampler.combobox

import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Notification
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

@ExtendWith(ChromeExtension)
class ComboBoxCustomOptionsUiTest extends BaseSamplerUiTest {

    public static final String AMOUNT = "1,000"
    public static final String AGE = "twenty"
    public static final String STANDARD = "Standard"

    @Test
    @DisplayName("Checks selection value specified by setOptionsList method for ComboBox")
    void checkComboBoxSetOptionsList() {
        openSample('combobox-custom-options')
        $j(ComboBox.class, 'amountComboBox')
                .shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(byText(AMOUNT))
                .shouldHave(value(AMOUNT))
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("Order.amount = 1000"))
    }

    @Test
    @DisplayName("Checks selection value specified by setOptionsMap method for ComboBox")
    void checkComboBoxSetOptionsMap() {
        openSample('combobox-custom-options')
        $j(ComboBox.class, 'ageComboBox')
                .shouldBe(VISIBLE)
                .shouldHave(value('(not selected)'))
                .openOptionsPopup()
                .select(byText(AGE))
                .shouldHave(value(AGE))
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("Customer.age = 20"))
    }

    @Test
    @DisplayName("Checks selection value specified by setOptionsEnum method for ComboBox")
    void checkComboBoxSetOptionsEnum() {
        openSample('combobox-custom-options')
        $j(ComboBox.class, 'gradleComboBox')
                .shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(byText(STANDARD))
                .shouldHave(value(STANDARD))
        $j(Notification.class)
                .shouldBe(VISIBLE)
                .shouldHave(caption("Customer.grade = STANDARD"))
    }
}
