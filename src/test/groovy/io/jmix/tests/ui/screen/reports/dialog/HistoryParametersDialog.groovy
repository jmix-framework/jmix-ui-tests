package io.jmix.tests.ui.screen.reports.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class HistoryParametersDialog extends Composite<HistoryParametersDialog> {

    @Wire(path = "optionDialog_actions.export.SELECTED_ROWS")
    Button selected

    @Wire(path = "optionDialog_actions.export.ALL_ROWS")
    Button all

}
