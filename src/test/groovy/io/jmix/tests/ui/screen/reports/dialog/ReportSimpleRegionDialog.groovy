package io.jmix.tests.ui.screen.reports.dialog

import io.jmix.masquerade.Selectors
import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table
import io.jmix.masquerade.component.Tree
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.byText

class ReportSimpleRegionDialog extends Composite<ReportSimpleRegionDialog> implements TableActionsTrait {

    @Wire
    Tree entityTree

    @Wire
    Button addItem

    @Wire
    Button downItem

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire
    Table propertiesTable

    void clickTreeNode(String cellText) {
        entityTree.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .getNode(byText(cellText))
                .click()
    }

    static void clickButton(Button button) {
        button.shouldBe(VISIBLE)
                .shouldBe(ENABLED)
                .click()
    }

    void chooseAnyElements(List<String> elements) {
        for (String str : elements) {
            clickTreeNode(str)
            clickButton(addItem)
        }
    }

    void getOrderInGroupBox(List<String> stringList) {
        def rows = propertiesTable.getRows(Selectors.isVisible()).texts()
        assert rows == stringList
    }

}
