package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.text
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byRowColIndexes
import static io.jmix.masquerade.component.Table.SortDirection.ASCENDING

@ExtendWith(ChromeExtension)
class TableFormatterUiTest extends BaseSamplerUiTest {

    public static final String FIRST_NAME = 'Andy'
    public static final String LAST_NAME = 'Lewis'

    @Test
    @DisplayName("Check column formatting for table")
    void checkTableColumnFormatter() {
        openSample('table-formatter')
        def table = $j(Table.class, 'customerTable')
        table.sort('column_name', ASCENDING)
        table.getCell(byRowColIndexes(0, 0))
                .shouldBe(VISIBLE)
                .shouldHave(text(FIRST_NAME.toUpperCase()))
        table.getCell(byRowColIndexes(0, 1))
                .shouldBe(VISIBLE)
                .shouldHave(text(LAST_NAME.toLowerCase()))
    }
}
