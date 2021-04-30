package io.jmix.tests.ui.screen.administration.datatools.editors

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.EntityPicker
import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Selectors.byJTestId

class AtmosphericGasEditor extends Composite<AtmosphericGasEditor> {
    @Wire
    TextField volume

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire
    EntityPicker gas

    @Wire(path = "windowClose")
    Button cancel

    void openGasesLookup() {
        gas.delegate
                .findElement(byJTestId("entity_lookup"))
                .click()
    }
}
