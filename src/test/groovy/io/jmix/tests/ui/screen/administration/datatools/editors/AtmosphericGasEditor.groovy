package io.jmix.tests.ui.screen.administration.datatools.editors

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.EntityPicker
import io.jmix.masquerade.component.HasActions
import io.jmix.masquerade.component.TextField

class AtmosphericGasEditor extends Composite<AtmosphericGasEditor> {
    @Wire
    TextField volume

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire
    EntityPicker gas

    void openGasesLookup() {
        gas.triggerAction(new HasActions.Action("entity_lookup"))
    }
}
