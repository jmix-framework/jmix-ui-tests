package io.jmix.tests.ui.test.datatools.inspector

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.datatools.EntityInspectorBrowse
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.datatools.BaseDatatoolsUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,datatools'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class DatatoolsEntityInspectorShowModesUiTest extends BaseDatatoolsUiTest {

    @Test
    @DisplayName("Checks displaying deleted User in Entity Inspector Browser in different modes")
    void checkDifferentModes() {
        loginAsAdmin()

        $j(MainScreen).openUserBrowse()
        $j(UserBrowse).removeUser(USERNAME1)

        $j(MainScreen).openEntityInspectorBrowse()

        $j(EntityInspectorBrowse).with {
            findEntityByFilter(USER_ENTITY_NAME, USER_FULL_STRING)
            selectShowMode(NON_REMOVED_ONLY_MODE)
        }

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME1, USER_TABLE_JTEST_ID)
        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME2, USER_TABLE_JTEST_ID)

        $j(EntityInspectorBrowse).selectShowMode(REMOVED_ONLY_MODE)

        $j(EntityInspectorBrowse).checkRecordIsNotDisplayed(USERNAME2, USER_TABLE_JTEST_ID)
        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME1, USER_TABLE_JTEST_ID)

        $j(EntityInspectorBrowse).selectShowMode(ALL_MODE)

        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME2, USER_TABLE_JTEST_ID)
        $j(EntityInspectorBrowse).checkRecordIsDisplayed(USERNAME1, USER_TABLE_JTEST_ID)
    }
}
