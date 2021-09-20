package io.jmix.tests.ui.screen.administration.security.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class MenuPolicyEditor extends Composite<MenuPolicyEditor> {
    @Wire
    ComboBox menuItemField

    @Wire
    TextField policyGroupField

    @Wire
    TextField screenField

    @Wire
    Button commit

}
