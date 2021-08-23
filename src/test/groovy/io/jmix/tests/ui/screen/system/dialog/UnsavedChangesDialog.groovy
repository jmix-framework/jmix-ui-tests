package io.jmix.tests.ui.screen.system.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button

class UnsavedChangesDialog extends Composite<UnsavedChangesDialog> {

    @Wire(path = "optionDialog_discard")
    Button doNotSave

}
