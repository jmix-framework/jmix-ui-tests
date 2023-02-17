package io.jmix.tests.ui.test.login

import com.codeborne.selenide.Selenide
import io.jmix.masquerade.component.Notification
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.system.login.LoginScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "main.liquibase.contexts=base")
@ContextConfiguration(initializers = TestContextInitializer)
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
