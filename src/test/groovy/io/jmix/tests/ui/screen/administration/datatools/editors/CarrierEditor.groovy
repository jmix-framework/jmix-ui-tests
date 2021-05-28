package io.jmix.tests.ui.screen.administration.datatools.editors

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TabSheet
import io.jmix.masquerade.component.TextField
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait

class CarrierEditor extends Composite<CarrierEditor> implements TableActionsTrait {

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire
    TabSheet tablesTabSheet

    void openSpacePortsTab() {
        tablesTabSheet.getTab("Carrier.spacePorts").select()
    }
}
