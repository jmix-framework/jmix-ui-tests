package io.jmix.tests.sampler

import io.jmix.masquerade.Components
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.sampler.test_support.component.grouptable.SamplerGroupTable
import io.jmix.tests.sampler.test_support.component.grouptable.SamplerGroupTableImpl
import org.junit.jupiter.api.BeforeEach

import static com.codeborne.selenide.Selenide.open
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j

/**
 * Base class for UI tests of Sampler project
 */
abstract class BaseSamplerUiTest {

    @BeforeEach
    void beforeEach() {
        Components.register(SamplerGroupTable, { by -> new SamplerGroupTableImpl(by) })
    }

    /**
     * Opens sample by given id
     *
     * @param sampleId sample id
     */
    static void openSample(String sampleId) {
        open("/#main/sample?id=" + sampleId)
        setLanguage('English')
    }

    private static ComboBox setLanguage(String language) {
        $j(ComboBox.class, 'localesComboBox')
                .shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(language)
    }
}
