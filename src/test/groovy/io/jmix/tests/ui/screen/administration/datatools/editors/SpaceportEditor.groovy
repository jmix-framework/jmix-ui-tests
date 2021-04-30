package io.jmix.tests.ui.screen.administration.datatools.editors

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TabSheet
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class SpaceportEditor extends Composite<SpaceportEditor> implements TableActionsTrait {
    @Wire
    TextField name

    @Wire
    TextField latitude

    @Wire
    TextField longitude

    @Wire
    TabSheet tablesTabSheet

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel

    void openCarriersTab() {
        tablesTabSheet.getTab("SpacePort.carriers").select()
    }

}
