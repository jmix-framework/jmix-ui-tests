package io.jmix.tests.sampler.table

import io.jmix.masquerade.Conditions
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(ChromeExtension)
class TableGeneratedColumnUiTest extends BaseSamplerUiTest {

    public static final String ENGLISH = 'English'

    @Test
    @DisplayName("Check addGeneratedColumn for table")
    void checkTableAddGeneratedColumnFor() {
        openSample('table-generated-column')
        $j(ComboBox.class, 'languageComboBox')
                .shouldBe(EDITABLE)
                .openOptionsPopup()
                .select(ENGLISH)
        $j(ComboBox.class, 'languageComboBox')
                .shouldHave(value(ENGLISH))
    }
}
