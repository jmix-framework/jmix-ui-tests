package io.jmix.tests.ui.screen.administration.datatools.editors

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TabSheet
import io.jmix.tests.ui.screen.administration.datatools.traits.FieldsActionsTrait

class AtmosphereEditor extends Composite<AtmosphereEditor> implements FieldsActionsTrait {
    @Wire
    TabSheet tablesTabSheet

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel

    void openGasesTab() {
        tablesTabSheet.getTab("Atmosphere.gases").select()
    }

    void openGeneralTab() {
        tablesTabSheet.getTab("general").select()
    }

}
