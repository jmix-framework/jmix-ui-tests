package io.jmix.tests.ui.screen.application.wddisabledentity

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.FileUploadField
import io.jmix.masquerade.component.TextField


class WDDisabledEntityEditor extends Composite<WDDisabledEntityEditor> {
    @Wire
    TextField nameField

    @Wire
    FileUploadField fileField

    @Wire(path = "commitAndCloseBtn")
    Button ok
}
