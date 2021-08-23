package io.jmix.tests.ui.screen.reports.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class InputParametersDialog extends Composite<InputParametersDialog> {

    @Wire(path = "entity_lookup")
    Button entityPicker

    @Wire
    Button printReportButton

    @Wire
    Button cancelButton
}
