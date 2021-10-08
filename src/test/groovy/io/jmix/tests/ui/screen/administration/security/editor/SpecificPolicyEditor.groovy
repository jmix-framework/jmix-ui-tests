package io.jmix.tests.ui.screen.administration.security.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class SpecificPolicyEditor extends Composite<SpecificPolicyEditor> {

    @Wire
    ComboBox resourceField

    @Wire
    TextField policyGroupField

    @Wire(path = ["dialog_sec_SpecificResourcePolicyModel.edit", "editActions", "windowCommitAndClose"])
    Button ok

}
