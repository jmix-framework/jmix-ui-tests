package io.jmix.tests.ui.screen.administration.emailtemplates.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField

class EmailTemplateGroupEditDialog extends Composite<EmailTemplateGroupEditDialog> {

    @Wire
    TextField name

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel
}