package io.jmix.tests.ui.screen.system.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class ConfirmationDialog extends Composite<ConfirmationDialog> {

    @Wire(path = "optionDialog_yes")
    public Button yes

    @Wire(path = "optionDialog_no")
    public Button no
}
