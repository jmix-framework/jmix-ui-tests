package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.GroupTable
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class GroupTableUiTest extends BaseSamplerUiTest {

    public static final String POTTER = "Potter"
    public static final String HIGH = "High"

    @Test
    @DisplayName("Checks expand and collapse all rows of group table")
    void checkGroupTableExpandCollapseRows() {
        openSample('grouptable-simple')
        def table = $j(GroupTable.class, 'customerTable')
        table.expandAll()
        def row = table
                .asTable()
                .getRow(byText(POTTER))
        row.shouldBe(VISIBLE)
        table.collapseAll()
        row.shouldNotBe(VISIBLE)
    }

    @Test
    @DisplayName("Checks expand and collapse row of group table")
    void checkGroupTableExpandCollapseOneRow() {
        openSample('grouptable-simple')
        def table = $j(GroupTable.class, 'customerTable')
        table.expand(withText(HIGH))
        def row = table
                .asTable()
                .getRow(byText(POTTER))
        row.shouldBe(VISIBLE)
        table.collapse(withText(HIGH))
        row.shouldNotBe(VISIBLE)
    }
}
