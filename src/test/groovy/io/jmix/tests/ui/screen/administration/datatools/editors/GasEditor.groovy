package io.jmix.tests.ui.screen.administration.datatools.editors

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField

class GasEditor extends Composite<GasEditor> {

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire
    TextField name
}
