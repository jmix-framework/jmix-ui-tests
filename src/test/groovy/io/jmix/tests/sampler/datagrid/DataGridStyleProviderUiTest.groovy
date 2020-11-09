package io.jmix.tests.sampler.datagrid

import com.codeborne.selenide.Condition
import io.jmix.masquerade.component.DataGrid
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.withText

@ExtendWith(ChromeExtension)
class DataGridStyleProviderUiTest extends BaseSamplerUiTest {

    public static final String STANDARD = "Standard"
    public static final String HIGH = "High"
    public static final String PREMIUM = "Premium"
    public static final String BLUE_COLOR = 'rgba(0, 0, 255, 1)'
    public static final String GREEN_COLOR = 'rgba(0, 128, 0, 1)'
    public static final String RED_COLOR = 'rgba(255, 0, 0, 1)'

    @Test
    @DisplayName("Check color of DataGrid column depending on value")
    void checkDataGridWithStyleProvider() {
        openSample('datagrid-style-provider')
        def dataGrid = $j(DataGrid.class, 'customersDataGrid')
        [
                [STANDARD, BLUE_COLOR],
                [PREMIUM, RED_COLOR],
                [HIGH, GREEN_COLOR]
        ].each {
            dataGrid
                    .getCell(withText(it[0]))
                    .shouldHave(Condition.cssValue('background-color', it[1]))
        }
    }
}
