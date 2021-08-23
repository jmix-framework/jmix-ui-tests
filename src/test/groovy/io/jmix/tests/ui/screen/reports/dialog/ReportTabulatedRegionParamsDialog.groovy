package io.jmix.tests.ui.screen.reports.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Tree

import static io.jmix.masquerade.Selectors.byText

class ReportTabulatedRegionParamsDialog extends Composite<ReportTabulatedRegionParamsDialog>{

    @Wire
    Tree entityTree
    @Wire(path="lookupSelectAction")
    Button ok

    void clickTreeNode(String cellText){
        entityTree.getNode(byText(cellText)).click()
    }
}
