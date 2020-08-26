package io.jmix.tests.ui


import io.jmix.masquerade.component.Notification
import io.jmix.tests.ui.extension.DefaultCleanupExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.caption
import static io.jmix.masquerade.Selectors.$j

@ExtendWith(DefaultCleanupExtension.class)
class LoginScreenUiTest extends BaseUiTest {

    @Test
    @DisplayName("Checks that the user cannot login with incorrect credentials")
    void loginWithIncorrectCredentials() {
        login('login', '1')

        $j(Notification.class)
                .shouldHave(caption('Login failed'))
    }
}
