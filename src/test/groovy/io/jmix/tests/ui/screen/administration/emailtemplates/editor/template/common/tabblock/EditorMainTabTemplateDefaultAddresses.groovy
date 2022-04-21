package io.jmix.tests.ui.screen.administration.emailtemplates.editor.template.common.tabblock

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.TextField

class EditorMainTabTemplateDefaultAddresses extends Composite<EditorMainTabTemplateDefaultAddresses> {

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
}
