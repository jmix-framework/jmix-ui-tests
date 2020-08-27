package io.jmix.tests.sampler

import io.jmix.tests.base.BaseTest

import static com.codeborne.selenide.Selenide.open

/**
 * Base class for UI tests of Sampler project
 */
abstract class BaseSamplerUiTest extends BaseTest {

    /**
     * Opens sample by given id
     *
     * @param sampleId sample id
     */
    static void openSample(String sampleId) {
        open("/#main/sample?id=" + sampleId)
    }
}
