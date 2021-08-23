package io.jmix.tests.ui.screen.reports.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class ValueFormatEditor extends Composite<ValueFormatEditor> {

    @Wire
    TextField valueNameField

    @Wire
    ComboBox formatField

    @Wire(path = ["editActions", "windowCommitAndClose"])
    Button ok

}
