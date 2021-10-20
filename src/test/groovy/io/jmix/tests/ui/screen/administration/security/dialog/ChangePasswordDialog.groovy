package io.jmix.tests.ui.screen.administration.security.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField

class ChangePasswordDialog extends Composite<ChangePasswordDialog> {

    @Wire
    TextField passwordField

    @Wire
    TextField confirmPasswordField

    @Wire
    Button okBtn

}
