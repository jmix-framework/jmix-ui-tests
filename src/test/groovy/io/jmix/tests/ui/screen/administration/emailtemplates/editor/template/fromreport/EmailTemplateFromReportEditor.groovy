package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromreport

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TabSheet

class EmailTemplateFromReportEditor extends Composite<EmailTemplateFromReportEditor> {

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel

    @Wire
    TabSheet tabsheet
}
