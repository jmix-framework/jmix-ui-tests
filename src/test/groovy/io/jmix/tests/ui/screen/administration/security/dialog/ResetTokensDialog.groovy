package io.jmix.tests.ui.screen.administration.security.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class ResetTokensDialog extends Composite<ResetTokensDialog> {

    @Wire(path = "optionDialog_resetRememberMeResetDialog.ResetOptionSelected")
    Button forSelected
}
