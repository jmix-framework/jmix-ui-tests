package io.jmix.tests.ui.screen.administration.security.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox

class RowLevelPolicyEditor extends Composite<RowLevelPolicyEditor> {
    @Wire
    ComboBox typeField

    @Wire
    ComboBox entityNameField

    @Wire
    ComboBox actionField

    @Wire(path = ["dialog_sec_RowLevelPolicyModel.edit", "editActions", "windowCommitAndClose"])
    Button ok
}
