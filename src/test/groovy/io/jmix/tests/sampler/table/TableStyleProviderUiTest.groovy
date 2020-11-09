package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.cssValue
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

@ExtendWith(ChromeExtension)
class TableStyleProviderUiTest extends BaseSamplerUiTest {

    public static final String STANDARD = "Standard"
    public static final String HIGH = "High"
    public static final String PREMIUM = "Premium"
    public static final String BLUE_COLOR = 'rgba(0, 0, 255, 1)'
    public static final String GREEN_COLOR = 'rgba(0, 128, 0, 1)'
    public static final String RED_COLOR = 'rgba(255, 0, 0, 1)'

    @Test
    @DisplayName("Check color of table column depending on value")
    void checkTableColumnStyleProvider() {
        openSample('table-style-provider')
        def table = $j(Table.class, 'customerTable')
        table
                .getCell(byText(STANDARD))
                .shouldHave(cssValue('background-color', BLUE_COLOR))
        table
                .getCell(byText(PREMIUM))
                .shouldHave(cssValue('background-color', RED_COLOR))
        table
                .getCell(byText(HIGH))
                .shouldHave(cssValue('background-color', GREEN_COLOR))
    }
}
