package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.fromdesigner.tabs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.FileUploadField
import io.jmix.masquerade.component.TextField

class EditorFromDesignerMainTab extends Composite<EditorFromDesignerMainTab> {

    @Wire
    FileUploadField fileUpload

    @Wire
    Button exportHtml

    @Wire
    Button viewHtml

    @Wire
    Button exportReport

}
