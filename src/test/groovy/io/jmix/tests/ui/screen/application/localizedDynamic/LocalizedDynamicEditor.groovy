package io.jmix.tests.ui.screen.application.localizedDynamic

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.ComboBox

class LocalizedDynamicEditor extends Composite<LocalizedDynamicEditor> {
    @Wire(path = "dynamicAttributesPanel_categoryField")
    ComboBox categoryField
}
