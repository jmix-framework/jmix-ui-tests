package io.jmix.tests.ui.test

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import io.jmix.masquerade.Components
import io.jmix.tests.ui.screen.system.login.LoginScreen
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test_support.component.checkbox.UiCheckBox
import io.jmix.tests.ui.test_support.component.checkbox.UiCheckBoxImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.Dimension

import static io.jmix.masquerade.Selectors.$j

/**
 * Base class for UI tests in ui package
 */
abstract class BaseUiTest {

    /**
     * Login as Administrator
     */
    static void loginAsAdmin() {
        Selenide.open('/')
        $j(LoginScreen).loginAsAdmin()
    }

    /**
     * Login as Administrator with Russian locale
     */
    static void loginAsAdminRus() {
        Selenide.open('/')
        $j(LoginScreen).loginWithLocale('Russian')
    }

    /**
     * Login as custom user
     */
    static void loginAsCustomUser(String login, String password) {
        Selenide.open('/')
        $j(LoginScreen).login(login, password)
    }

    /**
     * Sets window size to 1920x1080
     */
    static void maximizeWindowSize() {
        WebDriverRunner.webDriver.manage().window().setSize(new Dimension(1920, 1080))
    }

    @BeforeEach
    void beforeEach() {
        Components.register(UiCheckBox, { by -> new UiCheckBoxImpl(by) })
    }

    /**
     * Logout after each test
     */
    @AfterEach
    void logout() {
        $j(MainScreen).logout()
    }
}
