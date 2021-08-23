package io.jmix.tests.ui.screen.reports.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TabSheet
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class ReportEditor extends Composite<ReportEditor> implements TableActionsTrait {

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire
    Button run

    @Wire(path = "windowClose")
    Button cancel

    @Wire
    ComboBox group

    @Wire
    TabSheet tabsheet

    @Wire
    TextField name

    void openTab(String tabName) {
        tabsheet.getTab(tabName).select()
    }
}
