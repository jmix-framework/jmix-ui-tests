package io.jmix.tests.ui.screen.reports.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TabSheet
import io.jmix.masquerade.component.TextField
import io.jmix.masquerade.component.Tree
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

import static io.jmix.masquerade.Selectors.byText

class ReportEditor extends Composite<ReportEditor> implements TableActionsTrait {

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire
    Button run

    @Wire(path = "windowClose")
    Button cancel

    @Wire(path = ["scrollBox","create"])
    Button createTemplate

    @Wire(path = ["bandDetailsBox","create"])
    Button createBand

    @Wire(path = "createBandDefinition")
    Button createReportBand

    @Wire
    Tree bandsTree

    @Wire
    ComboBox group

    @Wire
    TabSheet tabsheet

    @Wire
    TextField name

    void openTab(String tabName) {
        tabsheet.getTab(tabName).select()
    }

    void clickBandsTreeNode(String cellText){
        bandsTree.getNode(byText(cellText)).click()
    }
}
