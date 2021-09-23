package io.jmix.tests.ui.screen.administration.security.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class EntityResourcePolicyDialog extends Composite<EntityResourcePolicyDialog> {
    @Wire
    ComboBox entityField

    @Wire
    ComboBox actionField

    @Wire
    TextField policyGroupField

    @Wire(path = ["dialog_sec_EntityResourcePolicyModel.edit", "editActions", "windowCommitAndClose"])
    Button ok
}
