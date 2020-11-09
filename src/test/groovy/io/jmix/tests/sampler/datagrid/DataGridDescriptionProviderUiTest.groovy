package io.jmix.tests.sampler.datagrid

import io.jmix.masquerade.component.DataGrid
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.interactions.Actions

import static com.codeborne.selenide.Condition.exactText
import static com.codeborne.selenide.Selenide.$x
import static com.codeborne.selenide.WebDriverRunner.webDriver
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byRowColIndexes
import static io.jmix.masquerade.component.DataGrid.SortDirection.ASCENDING

@ExtendWith(ChromeExtension)
class DataGridDescriptionProviderUiTest extends BaseSamplerUiTest {

    public static final String FULL_NAME = 'Andy Lewis'
    public static final String AGE_DESCRIPTION = "Age: 35"
    public static final String IS_ACTIVE_DESCRIPTION = "Active: No"
    public static final String GRADE_DESCRIPTION = "Grade: Standard"

    @Test
    @DisplayName("Check description for DataGrid column")
    void checkDataGridDescriptionProvider() {
        openSample('datagrid-description-provider')
        [
                [FULL_NAME, 0],
                [FULL_NAME, 1],
                [AGE_DESCRIPTION, 2],
                [IS_ACTIVE_DESCRIPTION, 3],
                [GRADE_DESCRIPTION, 4]
        ].each {
            def nameCell = $j(DataGrid.class, 'customersDataGrid')
                    .sort('column_name', ASCENDING)
                    .getCell(byRowColIndexes(0, it[1] as int))
            Actions actions = new Actions(getWebDriver())
            actions
                    .moveToElement(nameCell)
                    .perform()
            $x("//*[contains(@class, 'v-tooltip-text')]")
                    .shouldHave(exactText(it[0] as String))
        }
    }
}
