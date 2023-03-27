package io.jmix.tests.ui.test

import com.codeborne.selenide.WebDriverRunner
import io.jmix.masquerade.Components
import io.jmix.tests.ui.test_support.component.checkbox.UiCheckBox
import io.jmix.tests.ui.test_support.component.checkbox.UiCheckBoxImpl
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.Dimension
import org.springframework.test.context.ActiveProfiles

/**
 * Base class for UI tests in ui package
 */
@ActiveProfiles("test")
abstract class BaseUiTest {

    /**
     * Sets window size to 1920x1080
     */
    static void maximizeWindowSize() {
        WebDriverRunner.webDriver.manage().window().setSize(new Dimension(1920, 1080))
    }

    @BeforeEach
    void beforeEach() {
        // Register custom CheckBox component to avoid flaky behaviour
        Components.register(UiCheckBox, { by -> new UiCheckBoxImpl(by) })
    }
}
