package io.jmix.tests.ui.screen.administration.datatools.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class ConfirmExportDialog extends Composite<ConfirmExportDialog> {
    @Wire(path = "optionDialog_actions.export.SELECTED_ROWS")
    Button selectedRow
}
