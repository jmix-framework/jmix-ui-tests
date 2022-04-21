package io.jmix.tests.ui.screen.administration.emailtemplates.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TextField

class SendEmailTemplateDialog extends Composite<SendEmailTemplateDialog> {

    @Wire
    TextField subject

    @Wire
    TextField from

    @Wire
    TextField to

    @Wire
    TextField cc

    @Wire
    TextField bcc

    @Wire
    Button sendButton

    @Wire
    Button previewButton

    @Wire
    Button cancelButton
}
