package io.jmix.tests.ui.screen.dynattr

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.BaseUiTest
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.main.MainScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j
import static io.jmix.tests.ui.menu.Menus.DYNAMIC_ATTRIBUTES_BROWSE

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class test extends BaseUiTest {
    @Test
    @DisplayName("Creates new category")
    void createNewCategory() {
        loginAsAdmin()

        $j(MainScreen).with {
            sideMenu.openItem(DYNAMIC_ATTRIBUTES_BROWSE)
                    .createBtn
                    .click()

        }
    }
}
