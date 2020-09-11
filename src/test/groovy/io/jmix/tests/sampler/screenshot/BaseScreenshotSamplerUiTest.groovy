package io.jmix.tests.sampler.screenshot

import com.assertthat.selenium_shutterbug.core.Shutterbug
import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.WebDriverRunner
import io.jmix.tests.sampler.BaseSamplerUiTest

abstract class BaseScreenshotSamplerUiTest extends BaseSamplerUiTest {

    static final String STANDARD_FOLDER_PATH = System.getProperty("jmix.tests.sampler.screenshot.standardFolderPath")
    static final String DIFF_FOLDER_PATH = System.getProperty("jmix.tests.sampler.screenshot.diffFolderPath")
    static final String DIFF_PREFIX = 'diff-'
    static final String IMAGE_EXTENSION = '.png'

    /**
     * Compares the provided image with standard image. If the standard image does not exist, the method creates a
     * standard image.
     *
     * @param element the web element to be photographed
     * @param name the image name
     * @return true if the provided image and standard image are strictly equal, or if the standard image does not exist
     */
    static boolean equalsScreenshotWithStandard(SelenideElement element, String name) {
        element.shouldBe(Condition.visible)

        String standardPath = STANDARD_FOLDER_PATH + name + IMAGE_EXTENSION
        File standardImage = new File(standardPath)
        if (standardImage.exists()) {
            String diffPath = DIFF_FOLDER_PATH + DIFF_PREFIX + name
            return Shutterbug.shootElement(WebDriverRunner.webDriver, element)
                    .equalsWithDiff(standardPath, diffPath)
        } else {
            Shutterbug.shootElement(WebDriverRunner.webDriver, element)
                    .withName(name)
                    .save(STANDARD_FOLDER_PATH)

            return true
        }
    }
}
