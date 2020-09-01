package io.jmix.tests.ui

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.main.MainScreen
import io.jmix.tests.ui.screen.role.RoleModelLookup
import io.jmix.tests.ui.screen.user.UserBrowse
import io.jmix.tests.ui.screen.user.UserEditor
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Condition.visible
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.withText
import static io.jmix.tests.ui.menu.Menus.USER_BROWSE

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,test-role'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class UserUiTest extends BaseUiTest {

    @Test
    @DisplayName("Creates new user")
    void createNewUser() {
        loginAsAdmin()

        $j(MainScreen).with {
            sideMenu.openItem(USER_BROWSE)
                    .createUser()
        }

        $j(UserEditor).with {
            usernameField.setValue('newUsername')
            passwordField.setValue('qO4Hn6o')
            confirmPasswordField.setValue('qO4Hn6o')

            addBtn.click()

            $j(RoleModelLookup).with {
                roleModelsTable.getCell(withText('test-selenium'))
                        .click()
                lookupSelectAction.click()
            }

            windowCommitAndClose.click()
        }

        $j(UserBrowse).usersTable
                .getCell(withText('newUsername'))
                .shouldBe(visible)
    }
}
