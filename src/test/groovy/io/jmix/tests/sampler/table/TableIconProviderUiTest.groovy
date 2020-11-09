package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byRowColIndexes

@ExtendWith(ChromeExtension)
class TableIconProviderUiTest extends BaseSamplerUiTest {

    @Test
    @DisplayName("Check icon visibility in the table")
    void checkTableIconVisibility() {
        openSample('table-icon-provider')
        $j(Table.class, 'customerTable')
                .getCell(byRowColIndexes(0, 0))
                .$('.v-icon')
                .shouldBe(VISIBLE)
    }
}
