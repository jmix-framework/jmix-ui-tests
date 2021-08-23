package io.jmix.tests.ui.screen.reports.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.CheckBox

class ReportTemplateEditor extends Composite<ReportTemplateEditor>{
    @Wire
    CheckBox alterableField

    @Wire(path = ["editActions","windowCommitAndClose"])
    Button ok
}
