package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.common.tabblock

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class EditorMainTabTemplateDetailsBlock extends Composite<EditorMainTabTemplateDetailsBlock> {

    @Wire
    TextField name

    @Wire
    TextField code

    @Wire
    ComboBox group
}
