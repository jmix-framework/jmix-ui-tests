package io.jmix.tests.ui.screen.reports.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class ReportParameterEditor extends Composite<ReportParameterEditor> {
    @Wire
    TextField name

    @Wire
    TextField alias

    @Wire
    ComboBox parameterTypeField

    @Wire(path = ["editActions", "windowCommitAndClose"])
    Button ok

}
