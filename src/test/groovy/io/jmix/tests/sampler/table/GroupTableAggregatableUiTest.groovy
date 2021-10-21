package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.GroupTable
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import io.jmix.tests.sampler.test_support.component.grouptable.SamplerGroupTable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Condition.value
import static com.codeborne.selenide.Selenide.$x
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText
import static org.openqa.selenium.Keys.ENTER

@ExtendWith(ChromeExtension)
class GroupTableAggregatableUiTest extends BaseSamplerUiTest {

    public static final String HIGH = "High"
    public static final String COMMON = "Common"

    @Test
    @DisplayName("Check changing aggregation value of group table")
    void checkGroupTableChangeAggregationValue() {
        openSample('grouptable-aggregatable')
        $j(GroupTable.class, 'employeesTable')
                .shouldBe(VISIBLE)
        $x("//*[contains(@class, 'jmix-total-aggregation-textfield')]")
                .shouldBe(VISIBLE)
                .setValue("600")
                .sendKeys(ENTER)
        //TODO https://github.com/Haulmont/jmix-ui/issues/160
        $x("//*[contains(@class, 'jmix-group-aggregation-textfield')]")
                .shouldHave(value("304"))
        checkRowValue(HIGH, "72")
        checkRowValue(COMMON, "80")
    }

    @Test
    @DisplayName("Check changing aggregation department value of group table")
    void checkGroupTableAggregationDepartmentValueChange() {
        openSample('grouptable-aggregatable')
        $j(GroupTable.class, 'employeesTable')
                .shouldBe(VISIBLE)
        $x("//*[contains(@class, 'jmix-group-aggregation-textfield')]")
                .shouldBe(VISIBLE)
                .setValue("300")
                .sendKeys(ENTER)
        // TODO https://github.com/Haulmont/jmix-ui/issues/160
        $x("//*[contains(@class, 'jmix-total-aggregation-textfield')]")
                .shouldHave(value("5,300"))
        checkRowValue(HIGH, "90")
        checkRowValue(COMMON, "60")
    }

    private void checkRowValue(String expirience, String salary) {
        $j(SamplerGroupTable.class, 'employeesTable')
                .expandAllWithInterval(200)
                .asTable()
                .getRow(byText(expirience))
                .shouldHave(text(salary))
    }
}
