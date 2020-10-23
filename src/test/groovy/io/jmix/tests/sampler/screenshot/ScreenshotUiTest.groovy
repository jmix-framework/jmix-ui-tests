package io.jmix.tests.sampler.screenshot

import io.jmix.tests.extension.ChromeExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import java.util.stream.Stream

import static io.jmix.masquerade.Selectors.$j
import static org.junit.jupiter.api.Assertions.assertTrue

@ExtendWith(ChromeExtension)
class ScreenshotUiTest extends BaseScreenshotSamplerUiTest {

    protected static Stream<Arguments> provideTestArguments() {
        return Stream.of(
                Arguments.of('button-simple', 'helloButton', 'button-simple'),
                Arguments.of('button-simple', 'saveButton1', 'button-save-with-caption'),
                Arguments.of('button-simple', 'saveButton2', 'button-save-with-icon')
        )
    }

    @ParameterizedTest(name = "Compares a component {1} screenshot of sample screen {0} with {2} standard image")
    @MethodSource('provideTestArguments')
    void compareScreenshotTest(String sampleId, String jTestId, String imageName) {
        openSample(sampleId)
        assertTrue(
                equalsScreenshotWithStandard($j(jTestId), imageName),
                String.format("Component and standard screenshots are different. Go to the (%s) file to see the " +
                        "differences", diffDirPath(imageName))
        )
    }
}
