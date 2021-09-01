package io.jmix.tests.ui.screen.search.searchScreen


import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.TextField

import static com.codeborne.selenide.Condition.textCaseSensitive
import static com.codeborne.selenide.Selectors.byClassName
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byChain
import static io.jmix.masquerade.Selectors.byJTestId

class SearchScreen extends Composite<SearchScreen> {

    public static final int FIVE_SECONDS = 5000
    @Wire
    TextField inputField

    static void waitQuarts() {
        sleep(FIVE_SECONDS)
    }

    static void search() {
        $(byJTestId('searchButton'))
                .click()
    }

    void findByValue(String valueToFind) {
        inputField
                .shouldBe(VISIBLE)
                .setValue(valueToFind)
        search()
    }

    static void checkAndCloseSearchResult(String searchCriteria) {
        $j('tab_search_SearchResults.screen').with {
            shouldBe(VISIBLE)
            $(byJTestId('instanceButton'))
                    .shouldHave(textCaseSensitive(searchCriteria))
            $(byChain(byJTestId('tab_search_SearchResults.screen'),
                    byClassName('v-tabsheet-caption-close')))
                    .click()
        }
    }
}
