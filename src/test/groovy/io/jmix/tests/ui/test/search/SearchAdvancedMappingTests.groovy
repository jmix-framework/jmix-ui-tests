package io.jmix.tests.ui.test.search


import io.jmix.masquerade.component.Notification
import io.jmix.masquerade.component.TextField
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.extension.LocallyRunExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.search.personAdvancedMapping.PersonAdvancedMappingBrowser
import io.jmix.tests.ui.screen.search.personAdvancedMapping.PersonAdvancedMappingEditor
import io.jmix.tests.ui.screen.search.personProgrammicMapping.PersonProgrammicMappingBrowser
import io.jmix.tests.ui.screen.search.personProgrammicMapping.PersonProgrammicMappingEditor
import io.jmix.tests.ui.screen.search.searchScreen.SearchScreen
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['spring.quartz.job-store-type=jdbc',
                'spring.quartz.jdbc.initialize-schema=always',
                'main.datasource.studio.liquibase.excludePrefixes = qrtz_',
                'logging.level.io.jmix.search = debug',
                'spring.quartz.properties.org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.PostgreSQLDelegate',
                'jmix.localfs.storageDir = resources/io/jmix/tests/attaches',
                'jmix.liquibase.contexts=base,search'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)

class SearchAdvancedMappingTests extends BaseUiTest {

    public static final String PETROV = "Petrov"
    public static final String IVAN = 'Ivan'
    public static final String SOME_DESCRIPTION = 'someDescription'
    public static final String SAMARA = 'Samara'
    public static final String RUSSIA = 'Russia'
    public static final String GASTELO = 'Gastelo'
    public static final String GOLD = 'Gold'
    public static final String ENTITY_LOOKUP = 'entityLookup'
    public static final String SEARCH_USER = 'SearchUser'
    public static final String SELECTED_USER = 'user search [SearchUser]'
    public static final String SECOND_PDF = 'second.pdf'
    public static final String THIRD_TXT = 'third.txt'
    public static final String THIRD = 'third'
    public static final String SECOND = 'second'
    public static final String FIRST_PDF = 'first.pdf'
    public static final String FIRST = 'first'
    public static final String USER_EMAIL = "someSearchUser@email.com"
    public static final String FIRST_FILE_CONTENT = "This is a small demonstration .pdf file"
    public static final String SECOND_FILE_CONTENT = "February 20, 1999"
    public static final String THIRD_FILE_CONTENT = "programmigFileContext"
    public static final String NO_RESULTS = 'No results'
    public static final String SIDOROV = 'Sidorov'
    public static final String KIRILL = 'Kirill'
    public static final String PROGRAMMIC_DESCR = 'ProgrammicDescr'
    public static final String LENINA = 'Lenina'
    public static final String TOLLIATI = 'Tolliati'
    public static final String RF = 'Russian Federation'
    public static final String SILVER = 'Silver'

    @Test
    @DisplayName("Checks advanced mapping of search configured by annotations")
    void checkAdvancedMappingUsingAnnotationSearch() {
        loginAsAdmin()
        $j(MainScreen).openPersonAdvancedMapping()
        $j(PersonAdvancedMappingBrowser).with {
            editPerson(PETROV)
            $j(PersonAdvancedMappingEditor).with {
                [
                        [firstNameField, IVAN],
                        [descriptionField, SOME_DESCRIPTION],
                        [addressStreetField, GASTELO],
                        [addressCityField, SAMARA],
                        [addressCountryField, RUSSIA]]
                        .each {
                            (it[0] as TextField)
                                    .setValue(it[1] as String)
                        }
                gradeField
                        .shouldBe(VISIBLE)
                        .openOptionsPopup()
                        .select(GOLD)
                userField
                        .shouldBe(VISIBLE)
                $(byChain(byJTestId('userField' as String), byJTestId(ENTITY_LOOKUP)))
                        .shouldBe(VISIBLE)
                        .click()
                $j(UserBrowse).with {
                    usersTable
                            .shouldBe(VISIBLE)
                            .selectRow(byCells(SEARCH_USER))
                    lookupSelectAction
                            .shouldBe(VISIBLE)
                            .click()
                }
                userField.shouldHave(valueContains(SELECTED_USER))
                pictureField
                        .shouldBe(VISIBLE)
                        .shouldHave(textCaseSensitive(SECOND_PDF))
                pictureSecField
                        .shouldBe(VISIBLE)
                        .shouldHave(textCaseSensitive(FIRST_PDF))
                saveChanges()
            }
            personAdvancedMappingsTable
                    .shouldBe(VISIBLE)
                    .getRow(byCells(IVAN, SOME_DESCRIPTION, GOLD, SAMARA, SELECTED_USER))
        }
        $j(MainScreen).openSearchScreen()
        $j(SearchScreen).with {
            waitQuarts()
            [IVAN, PETROV, SAMARA, RUSSIA, GOLD, FIRST, SECOND, FIRST_FILE_CONTENT, SEARCH_USER]
                    .each {
                        findByValue(it)
                        checkAndCloseSearchResult(IVAN)
                    }
            [USER_EMAIL, SOME_DESCRIPTION, SECOND_FILE_CONTENT]
                    .each {
                        findByValue(it)
                        $j(Notification.class)
                                .shouldBe(VISIBLE)
                                .shouldHave(caption(NO_RESULTS))
                                .clickToClose()
                    }
        }
    }

    @Test
    @DisplayName("Checks mapping of search configured by builder")
    void checkMappingUsingBuilderSearch() {
        loginAsAdmin()
        $j(MainScreen).openPersonProgrammingMapping()
        $j(PersonProgrammicMappingBrowser).with {
            editPerson(SIDOROV)
            $j(PersonProgrammicMappingEditor).with {
                [
                        [firstNameField, KIRILL],
                        [descriptionField, PROGRAMMIC_DESCR],
                        [addressStreetField, LENINA],
                        [addressCityField, TOLLIATI],
                        [addressCountryField, RF]]
                        .each {
                            (it[0] as TextField)
                                    .setValue(it[1] as String)
                        }
                gradeField
                        .shouldBe(VISIBLE)
                        .openOptionsPopup()
                        .select(SILVER)
                userField
                        .shouldBe(VISIBLE)
                $(byChain(byJTestId('userField' as String), byJTestId(ENTITY_LOOKUP)))
                        .shouldBe(VISIBLE)
                        .click()
                $j(UserBrowse).with {
                    usersTable
                            .shouldBe(VISIBLE)
                            .selectRow(byCells(SEARCH_USER))
                    lookupSelectAction
                            .shouldBe(VISIBLE)
                            .click()
                }
                userField.shouldHave(valueContains(SELECTED_USER))
                pictureField
                        .shouldBe(VISIBLE)
                        .shouldHave(textCaseSensitive(THIRD_TXT))
                saveChanges()
            }
            personProgrammingMappingsTable
                    .shouldBe(VISIBLE)
                    .getRow(byCells(KIRILL, PROGRAMMIC_DESCR, SILVER, TOLLIATI, SELECTED_USER))
        }
        $j(MainScreen).openSearchScreen()
        $j(SearchScreen).with {
            waitQuarts()
            [SIDOROV, KIRILL, TOLLIATI, RF, SILVER, THIRD, THIRD_FILE_CONTENT]
                    .each {
                        findByValue(it)
                        checkAndCloseSearchResult(KIRILL)
                    }
            [USER_EMAIL, PROGRAMMIC_DESCR]
                    .each {
                        findByValue(it)
                        $j(Notification.class)
                                .shouldBe(VISIBLE)
                                .shouldHave(caption(NO_RESULTS))
                                .clickToClose()
                    }
        }
    }
}
