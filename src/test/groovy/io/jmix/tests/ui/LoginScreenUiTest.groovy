package io.jmix.tests.ui

import com.codeborne.selenide.Selenide
import io.jmix.masquerade.component.Notification
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.login.LoginScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class LoginScreenUiTest {

    @Test
    @DisplayName("Checks that the user cannot login with incorrect credentials")
    void loginWithIncorrectCredentials() {
        Selenide.open('/')
        $j(LoginScreen).login('username', 'password')

        $j(Notification.class)
                .shouldHave(caption('Login failed'))
                .clickToClose()
    }
}
