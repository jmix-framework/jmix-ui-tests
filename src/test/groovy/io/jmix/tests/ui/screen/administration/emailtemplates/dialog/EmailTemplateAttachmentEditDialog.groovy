package io.jmix.tests.ui.screen.administration.emailtemplates.dialog

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.FileUploadField
import io.jmix.masquerade.component.TextField

class EmailTemplateAttachmentEditDialog extends Composite<EmailTemplateAttachmentEditDialog> {

    @Wire
    FileUploadField uploadField

    @Wire
    TextField nameField

    @Wire(path = ["dialog_emltmp_EmailTemplateAttachment.edit", "windowCommitAndClose"])
    Button ok

    @Wire(path = ["dialog_emltmp_EmailTemplateAttachment.edit", "windowClose"])
    Button cancel
}
