package io.jmix.tests.ui.screen.administration.emailtemplates.editor.block

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class EmailTemplateBlockEditor extends Composite<EmailTemplateBlockEditor> {

    @Wire
    TextField nameField

    @Wire
    TextField labelField

    @Wire
    ComboBox iconLookup

    @Wire
    ComboBox categoryField

    @Wire(path = "windowCommitAndClose")
    Button ok

    @Wire(path = "windowClose")
    Button cancel

}
