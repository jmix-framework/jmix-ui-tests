package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromdesigner

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TabSheet

class EmailTemplateFromDesignerEditor extends Composite<EmailTemplateFromDesignerEditor> {

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel

    @Wire
    TabSheet tabsheet
}
