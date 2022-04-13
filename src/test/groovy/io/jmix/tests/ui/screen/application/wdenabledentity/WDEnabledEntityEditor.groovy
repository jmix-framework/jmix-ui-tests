package io.jmix.tests.ui.screen.application.wdenabledentity

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.FileUploadField
import io.jmix.masquerade.component.TextField

class WDEnabledEntityEditor extends Composite<WDEnabledEntityEditor> {
    @Wire
    TextField nameField

    @Wire
    FileUploadField fileField

    @Wire(path = "commitAndCloseBtn")
    Button ok
}
